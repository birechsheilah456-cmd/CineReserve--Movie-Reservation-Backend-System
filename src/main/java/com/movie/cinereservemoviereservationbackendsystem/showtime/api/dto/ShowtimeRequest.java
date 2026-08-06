package com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ShowtimeRequest {

    @NotNull(message = "Movie ID is required.")
    private Long movieId;

    @NotNull(message = "Auditorium ID is required.")
    private Long auditoriumId;

    @NotNull(message = "Start time is required.")
    @Future(message = "Showtime start time must be in the future.")
    private LocalDateTime startTime;

    @NotNull(message = "Price is required.")
    @Positive(message = "Price must be greater than zero.")
    private Double price;
}