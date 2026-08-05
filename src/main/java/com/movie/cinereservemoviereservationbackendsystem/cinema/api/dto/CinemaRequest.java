package com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto;

import lombok.Data;

@Data
public class CinemaRequest {
    private String name;
    private String location;
    private String contactNumber;
}