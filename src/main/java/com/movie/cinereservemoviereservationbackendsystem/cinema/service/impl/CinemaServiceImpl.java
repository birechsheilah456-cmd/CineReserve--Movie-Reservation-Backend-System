package com.movie.cinereservemoviereservationbackendsystem.cinema.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto.CinemaRequest;
import com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto.CinemaResponse;
import com.movie.cinereservemoviereservationbackendsystem.cinema.model.Cinema;
import com.movie.cinereservemoviereservationbackendsystem.cinema.repository.CinemaRepository;
import com.movie.cinereservemoviereservationbackendsystem.cinema.service.CinemaService;
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
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;

    @Override
    @Transactional
    public CinemaResponse createCinema(CinemaRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Cinema name cannot be empty.");
        }
        if (cinemaRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateResourceException("Cinema with name '" + request.getName() + "' already exists.");
        }

        Cinema cinema = Cinema.builder()
                .name(request.getName())
                .location(request.getLocation())
                .contactNumber(request.getContactNumber())
                .build();

        Cinema savedCinema = cinemaRepository.save(cinema);
        return mapToResponse(savedCinema);
    }

    @Override
    @Transactional
    public CinemaResponse updateCinema(Long id, CinemaRequest request) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cinema not found with ID: " + id));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            cinema.setName(request.getName());
        }
        if (request.getLocation() != null) {
            cinema.setLocation(request.getLocation());
        }
        if (request.getContactNumber() != null) {
            cinema.setContactNumber(request.getContactNumber());
        }

        Cinema updatedCinema = cinemaRepository.save(cinema);
        return mapToResponse(updatedCinema);
    }

    @Override
    @Transactional
    public void deleteCinema(Long id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cinema not found with ID: " + id));

        // TODO: Add validation check later if auditoriums/showtimes are linked to this cinema before deleting.
        cinemaRepository.delete(cinema);
    }

    @Override
    public CinemaResponse getCinemaById(Long id) {
        Cinema cinema = cinemaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cinema not found with ID: " + id));
        return mapToResponse(cinema);
    }

    @Override
    public List<CinemaResponse> getAllCinemas() {
        return cinemaRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private CinemaResponse mapToResponse(Cinema cinema) {
        return CinemaResponse.builder()
                .id(cinema.getId())
                .name(cinema.getName())
                .location(cinema.getLocation())
                .contactNumber(cinema.getContactNumber())
                .build();
    }
}