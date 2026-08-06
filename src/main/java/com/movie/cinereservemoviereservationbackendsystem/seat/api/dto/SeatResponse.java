package com.movie.cinereservemoviereservationbackendsystem.seat.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SeatResponse {

    private Long id;

    private String seatNumber;

    private String rowLabel;

    private Integer seatPosition;

    private Long auditoriumId;

    private String auditoriumName;
}