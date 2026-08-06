package com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowtimeRequest {
    private Long movieId;
    private Long auditoriumId;
    private LocalDateTime startTime;
    private Double price;
}