package com.movie.cinereservemoviereservationbackendsystem.seat.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.model.Auditorium;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.repository.AuditoriumRepository;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.BusinessRuleViolationException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.DuplicateResourceException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatAvailabilityResponse;
import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatRequest;
import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatResponse;
import com.movie.cinereservemoviereservationbackendsystem.seat.model.Seat;
import com.movie.cinereservemoviereservationbackendsystem.seat.repository.SeatRepository;
import com.movie.cinereservemoviereservationbackendsystem.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final AuditoriumRepository auditoriumRepository;

    @Override
    @Transactional
    public SeatResponse createSeat(SeatRequest request) {

        if (request.getSeatNumber() == null || request.getSeatNumber().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Seat number cannot be empty.");
        }

        Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Auditorium not found with ID: " + request.getAuditoriumId()));

        if (seatRepository.findBySeatNumberAndAuditorium(request.getSeatNumber(), auditorium).isPresent()) {
            throw new DuplicateResourceException(
                    "Seat '" + request.getSeatNumber() + "' already exists in this auditorium.");
        }

        Seat seat = Seat.builder()
                .seatNumber(request.getSeatNumber())
                .rowLabel(request.getRowLabel())
                .seatPosition(request.getSeatPosition())
                .auditorium(auditorium)
                .build();

        return mapToResponse(seatRepository.save(seat));
    }

    @Override
    @Transactional
    public SeatResponse updateSeat(Long id, SeatRequest request) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found with ID: " + id));

        if (request.getSeatNumber() != null && !request.getSeatNumber().trim().isEmpty()) {
            seat.setSeatNumber(request.getSeatNumber());
        }

        if (request.getRowLabel() != null) {
            seat.setRowLabel(request.getRowLabel());
        }

        if (request.getSeatPosition() != null) {
            seat.setSeatPosition(request.getSeatPosition());
        }

        if (request.getAuditoriumId() != null) {
            Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Auditorium not found with ID: " + request.getAuditoriumId()));

            seat.setAuditorium(auditorium);
        }

        return mapToResponse(seatRepository.save(seat));
    }

    @Override
    @Transactional
    public void deleteSeat(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found with ID: " + id));

        seatRepository.delete(seat);
    }

    @Override
    public SeatResponse getSeatById(Long id) {

        Seat seat = seatRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Seat not found with ID: " + id));

        return mapToResponse(seat);
    }

    @Override
    public List<SeatResponse> getAllSeats() {

        return seatRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SeatAvailabilityResponse> getSeatsByAuditorium(Long auditoriumId) {

        return seatRepository.findByAuditoriumId(auditoriumId)
                .stream()
                .map(seat -> SeatAvailabilityResponse.builder()
                        .seatId(seat.getId())
                        .seatNumber(seat.getSeatNumber())
                        .rowLabel(seat.getRowLabel())
                        .seatPosition(seat.getSeatPosition())
                        .available(true)
                        .build())
                .collect(Collectors.toList());
    }

    private SeatResponse mapToResponse(Seat seat) {

        return SeatResponse.builder()
                .id(seat.getId())
                .seatNumber(seat.getSeatNumber())
                .rowLabel(seat.getRowLabel())
                .seatPosition(seat.getSeatPosition())
                .auditoriumId(seat.getAuditorium().getId())
                .auditoriumName(seat.getAuditorium().getName())
                .build();
    }
}