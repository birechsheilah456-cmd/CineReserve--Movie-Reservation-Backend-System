package com.movie.cinereservemoviereservationbackendsystem.seat.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatAvailabilityResponse {

    private Long seatId;

    private String seatNumber;

    private String rowLabel;

    private Integer seatPosition;

    private boolean available;
}