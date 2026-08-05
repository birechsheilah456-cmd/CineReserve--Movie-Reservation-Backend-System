package com.movie.cinereservemoviereservationbackendsystem.movie.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieRequest;
import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieResponse;
import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieSummaryResponse;
import com.movie.cinereservemoviereservationbackendsystem.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest request) {
        MovieResponse response = movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable Long id, @RequestBody MovieRequest request) {
        MovieResponse response = movieService.updateMovie(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        MovieResponse response = movieService.getMovieById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MovieSummaryResponse>> getAllMovies() {
        List<MovieSummaryResponse> response = movieService.getAllMovies();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<List<MovieSummaryResponse>> getMoviesByGenre(@PathVariable Long genreId) {
        List<MovieSummaryResponse> response = movieService.getMoviesByGenre(genreId);
        return ResponseEntity.ok(response);
    }
}