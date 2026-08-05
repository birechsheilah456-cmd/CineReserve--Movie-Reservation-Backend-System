package com.movie.cinereservemoviereservationbackendsystem.movie.service;

import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieRequest;
import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieResponse;
import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieSummaryResponse;

import java.util.List;

public interface MovieService {
    MovieResponse createMovie(MovieRequest request);
    MovieResponse updateMovie(Long id, MovieRequest request);
    void deleteMovie(Long id);
    MovieResponse getMovieById(Long id);
    List<MovieSummaryResponse> getAllMovies();
    List<MovieSummaryResponse> getMoviesByGenre(Long genreId);
}