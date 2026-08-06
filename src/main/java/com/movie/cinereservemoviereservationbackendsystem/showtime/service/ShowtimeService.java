package com.movie.cinereservemoviereservationbackendsystem.showtime.service;

import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeAvailabilityResponse;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeRequest;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

public interface ShowtimeService {

    ShowtimeResponse createShowtime(@Valid ShowtimeRequest request);

    ShowtimeResponse updateShowtime(Long id, ShowtimeRequest request);

    void deleteShowtime(Long id);

    ShowtimeResponse getShowtimeById(Long id);

    ShowtimeAvailabilityResponse getShowtimesByDate(LocalDate date);

    List<ShowtimeResponse> getUpcomingShowtimesForMovie(Long movieId);
}