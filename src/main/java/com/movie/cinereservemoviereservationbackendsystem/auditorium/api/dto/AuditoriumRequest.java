package com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto;

import lombok.Data;

@Data
public class AuditoriumRequest {
    private String name;
    private Integer capacity;
    private Long cinemaId;
}