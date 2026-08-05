package com.movie.cinereservemoviereservationbackendsystem.auditorium.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto.AuditoriumRequest;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto.AuditoriumResponse;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.model.Auditorium;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.repository.AuditoriumRepository;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.service.AuditoriumService;
import com.movie.cinereservemoviereservationbackendsystem.cinema.model.Cinema;
import com.movie.cinereservemoviereservationbackendsystem.cinema.repository.CinemaRepository;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.BusinessRuleViolationException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.DuplicateResourceException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuditoriumServiceImpl implements AuditoriumService {

    private final AuditoriumRepository auditoriumRepository;
    private final CinemaRepository cinemaRepository;

    @Override
    @Transactional
    public AuditoriumResponse createAuditorium(AuditoriumRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Auditorium name cannot be empty.");
        }
        if (request.getCapacity() == null || request.getCapacity() <= 0) {
            throw new BusinessRuleViolationException("Auditorium capacity must be greater than zero.");
        }

        Cinema cinema = cinemaRepository.findById(request.getCinemaId())
                .orElseThrow(() -> new ResourceNotFoundException("Cinema not found with ID: " + request.getCinemaId()));

        if (auditoriumRepository.existsByNameAndCinemaId(request.getName(), request.getCinemaId())) {
            throw new DuplicateResourceException("Auditorium with name '" + request.getName() + "' already exists in this cinema.");
        }

        Auditorium auditorium = Auditorium.builder()
                .name(request.getName())
                .capacity(request.getCapacity())
                .cinema(cinema)
                .build();

        Auditorium savedAuditorium = auditoriumRepository.save(auditorium);
        return mapToResponse(savedAuditorium);
    }

    @Override
    @Transactional
    public AuditoriumResponse updateAuditorium(Long id, AuditoriumRequest request) {
        Auditorium auditorium = auditoriumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auditorium not found with ID: " + id));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            auditorium.setName(request.getName());
        }
        if (request.getCapacity() != null) {
            if (request.getCapacity() <= 0) {
                throw new BusinessRuleViolationException("Auditorium capacity must be greater than zero.");
            }
            auditorium.setCapacity(request.getCapacity());
        }
        if (request.getCinemaId() != null) {
            Cinema cinema = cinemaRepository.findById(request.getCinemaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cinema not found with ID: " + request.getCinemaId()));
            auditorium.setCinema(cinema);
        }

        Auditorium updatedAuditorium = auditoriumRepository.save(auditorium);
        return mapToResponse(updatedAuditorium);
    }

    @Override
    @Transactional
    public void deleteAuditorium(Long id) {
        Auditorium auditorium = auditoriumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auditorium not found with ID: " + id));

        // TODO: Add check later for active showtimes or seats tied to this auditorium before deleting.
        auditoriumRepository.delete(auditorium);
    }

    @Override
    public AuditoriumResponse getAuditoriumById(Long id) {
        Auditorium auditorium = auditoriumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Auditorium not found with ID: " + id));
        return mapToResponse(auditorium);
    }

    @Override
    public List<AuditoriumResponse> getAllAuditoriums() {
        return auditoriumRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AuditoriumResponse> getAuditoriumsByCinema(Long cinemaId) {
        return auditoriumRepository.findByCinemaId(cinemaId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AuditoriumResponse mapToResponse(Auditorium auditorium) {
        return AuditoriumResponse.builder()
                .id(auditorium.getId())
                .name(auditorium.getName())
                .capacity(auditorium.getCapacity())
                .cinemaId(auditorium.getCinema() != null ? auditorium.getCinema().getId() : null)
                .cinemaName(auditorium.getCinema() != null ? auditorium.getCinema().getName() : null)
                .build();
    }
}