package com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class ShowtimeAvailabilityResponse {

    private LocalDate date;
    private List<ShowtimeResponse> availableShowtime;
}