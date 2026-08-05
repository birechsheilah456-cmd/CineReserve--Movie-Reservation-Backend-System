package com.movie.cinereservemoviereservationbackendsystem.movie.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.common.response.ApiResponse;
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
    public ResponseEntity<ApiResponse<MovieResponse>> createMovie(@RequestBody MovieRequest request) {
        MovieResponse response = movieService.createMovie(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Movie created successfully", response));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<ApiResponse<MovieResponse>> updateMovie(@PathVariable Long id, @RequestBody MovieRequest request) {
        MovieResponse response = movieService.updateMovie(id, request);
        return ResponseEntity.ok(ApiResponse.success("Movie updated successfully", response));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<ApiResponse<Void>> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return ResponseEntity.ok(ApiResponse.success("Movie deleted successfully", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MovieResponse>> getMovieById(@PathVariable Long id) {
        MovieResponse response = movieService.getMovieById(id);
        return ResponseEntity.ok(ApiResponse.success("Movie retrieved successfully", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MovieSummaryResponse>>> getAllMovies() {
        List<MovieSummaryResponse> response = movieService.getAllMovies();
        return ResponseEntity.ok(ApiResponse.success("Movies retrieved successfully", response));
    }

    @GetMapping("/genre/{genreId}")
    public ResponseEntity<ApiResponse<List<MovieSummaryResponse>>> getMoviesByGenre(@PathVariable Long genreId) {
        List<MovieSummaryResponse> response = movieService.getMoviesByGenre(genreId);
        return ResponseEntity.ok(ApiResponse.success("Movies retrieved by genre successfully", response));
    }
}