package com.movie.cinereservemoviereservationbackendsystem.genre.service;

import com.movie.cinereservemoviereservationbackendsystem.genre.api.dto.GenreRequest;
import com.movie.cinereservemoviereservationbackendsystem.genre.api.dto.GenreResponse;

import java.util.List;

public interface GenreService {
    GenreResponse createGenre(GenreRequest request);
    GenreResponse updateGenre(Long id, GenreRequest request);
    void deleteGenre(Long id);
    GenreResponse getGenreById(Long id);
    List<GenreResponse> getAllGenres();
}