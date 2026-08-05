package com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ReservationResponse {
    private Long id;
    private Long userId;
    private Long showtimeId;
    private ReservationStatus status;
    private Double totalPrice;
    private LocalDateTime reservationTime;
    private List<Long> seatIds;
}