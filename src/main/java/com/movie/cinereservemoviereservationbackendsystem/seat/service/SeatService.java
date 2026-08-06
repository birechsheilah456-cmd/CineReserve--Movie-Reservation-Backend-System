package com.movie.cinereservemoviereservationbackendsystem.seat.service;

import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatAvailabilityResponse;
import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatRequest;
import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatResponse;

import java.util.List;

public interface SeatService {

    SeatResponse createSeat(SeatRequest request);

    SeatResponse updateSeat(Long id, SeatRequest request);

    void deleteSeat(Long id);

    SeatResponse getSeatById(Long id);

    List<SeatResponse> getAllSeats();

    List<SeatAvailabilityResponse> getSeatsByAuditorium(Long auditoriumId);
}