package com.movie.cinereservemoviereservationbackendsystem.seat.api.dto;

import lombok.Data;

@Data
public class SeatRequest {

    private String seatNumber;

    private String rowLabel;

    private Integer seatPosition;

    private Long auditoriumId;
}