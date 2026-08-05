package com.movie.cinereservemoviereservationbackendsystem.cinema.service;

import com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto.CinemaRequest;
import com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto.CinemaResponse;

import java.util.List;

public interface CinemaService {
    CinemaResponse createCinema(CinemaRequest request);
    CinemaResponse updateCinema(Long id, CinemaRequest request);
    void deleteCinema(Long id);
    CinemaResponse getCinemaById(Long id);
    List<CinemaResponse> getAllCinemas();
}