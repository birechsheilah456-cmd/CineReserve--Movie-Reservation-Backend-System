package com.movie.cinereservemoviereservationbackendsystem.reservation.service;

import com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto.ReservationRequest;
import com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto.ReservationResponse;

import java.util.List;

public interface ReservationService {
    ReservationResponse createReservation(Long userId, ReservationRequest request);
    ReservationResponse getReservationById(Long id);
    List<ReservationResponse> getUserReservations(Long userId);
    ReservationResponse cancelReservation(Long id);
}