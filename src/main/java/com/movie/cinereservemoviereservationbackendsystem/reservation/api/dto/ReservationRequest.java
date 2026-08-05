package com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto;

import lombok.Data;
import java.util.List;

@Data
public class ReservationRequest {
    private Long showtimeId;
    private List<Long> seatIds;
    private String idempotencyKey;
    private Double totalPrice;
}