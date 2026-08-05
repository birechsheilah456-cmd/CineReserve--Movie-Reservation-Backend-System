package com.movie.cinereservemoviereservationbackendsystem.reservation.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.ReservationStatus;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.BusinessRuleViolationException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto.ReservationRequest;
import com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto.ReservationResponse;
import com.movie.cinereservemoviereservationbackendsystem.reservation.model.Reservation;
import com.movie.cinereservemoviereservationbackendsystem.reservation.model.ReservationSeat;
import com.movie.cinereservemoviereservationbackendsystem.reservation.repository.ReservationRepository;
import com.movie.cinereservemoviereservationbackendsystem.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    @Transactional
    public ReservationResponse createReservation(Long userId, ReservationRequest request) {
        // Idempotency check: return existing reservation if key matches
        if (request.getIdempotencyKey() != null && !request.getIdempotencyKey().isBlank()) {
            var existing = reservationRepository.findByIdempotencyKey(request.getIdempotencyKey());
            if (existing.isPresent()) {
                return mapToResponse(existing.get());
            }
        }

        if (request.getSeatIds() == null || request.getSeatIds().isEmpty()) {
            throw new BusinessRuleViolationException("A reservation must include at least one seat.");
        }

        Reservation reservation = Reservation.builder()
                .userId(userId)
                .showtimeId(request.getShowtimeId())
                .idempotencyKey(request.getIdempotencyKey())
                .totalPrice(request.getTotalPrice() != null ? request.getTotalPrice() : 0.0)
                .status(ReservationStatus.CONFIRMED)
                .build();

        List<ReservationSeat> seats = request.getSeatIds().stream()
                .map(seatId -> ReservationSeat.builder()
                        .seatId(seatId)
                        .reservation(reservation)
                        .build())
                .collect(Collectors.toList());

        reservation.setReservationSeats(seats);

        Reservation savedReservation = reservationRepository.save(reservation);
        return mapToResponse(savedReservation);
    }

    @Override
    public ReservationResponse getReservationById(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));
        return mapToResponse(reservation);
    }

    @Override
    public List<ReservationResponse> getUserReservations(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservationResponse cancelReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found with ID: " + id));

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new BusinessRuleViolationException("Reservation is already cancelled.");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        Reservation updated = reservationRepository.save(reservation);
        return mapToResponse(updated);
    }

    private ReservationResponse mapToResponse(Reservation reservation) {
        List<Long> seatIds = reservation.getReservationSeats() != null
                ? reservation.getReservationSeats().stream().map(ReservationSeat::getSeatId).collect(Collectors.toList())
                : List.of();

        return ReservationResponse.builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .showtimeId(reservation.getShowtimeId())
                .status(reservation.getStatus())
                .totalPrice(reservation.getTotalPrice())
                .reservationTime(reservation.getReservationTime())
                .seatIds(seatIds)
                .build();
    }
}