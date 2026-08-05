package com.movie.cinereservemoviereservationbackendsystem.movie.api.dto;

import lombok.Data;

@Data
public class MovieRequest {
    private String title;
    private String description;
    private String posterImage;
    private Integer duration;
    private Long genreId;
}