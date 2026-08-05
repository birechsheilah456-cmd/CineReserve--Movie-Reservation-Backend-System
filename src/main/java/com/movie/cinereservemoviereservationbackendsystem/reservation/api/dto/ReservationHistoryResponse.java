package com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.ReservationStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReservationHistoryResponse {
    private Long id;
    private Long showtimeId;
    private ReservationStatus status;
    private Double totalPrice;
    private LocalDateTime reservationTime;
}