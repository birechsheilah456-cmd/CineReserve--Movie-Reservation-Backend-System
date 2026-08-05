package com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuditoriumResponse {
    private Long id;
    private String name;
    private Integer capacity;
    private Long cinemaId;
    private String cinemaName;
}