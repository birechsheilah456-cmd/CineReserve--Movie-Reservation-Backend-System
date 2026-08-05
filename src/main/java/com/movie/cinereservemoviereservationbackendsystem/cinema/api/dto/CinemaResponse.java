package com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CinemaResponse {
    private Long id;
    private String name;
    private String location;
    private String contactNumber;
}