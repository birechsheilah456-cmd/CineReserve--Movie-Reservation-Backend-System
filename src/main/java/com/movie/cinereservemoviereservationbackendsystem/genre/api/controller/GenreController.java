package com.movie.cinereservemoviereservationbackendsystem.genre.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.genre.api.dto.GenreRequest;
import com.movie.cinereservemoviereservationbackendsystem.genre.api.dto.GenreResponse;
import com.movie.cinereservemoviereservationbackendsystem.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<GenreResponse> createGenre(@RequestBody GenreRequest request) {
        GenreResponse response = genreService.createGenre(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<GenreResponse> updateGenre(@PathVariable Long id, @RequestBody GenreRequest request) {
        GenreResponse response = genreService.updateGenre(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreResponse> getGenreById(@PathVariable Long id) {
        GenreResponse response = genreService.getGenreById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<GenreResponse>> getAllGenres() {
        List<GenreResponse> response = genreService.getAllGenres();
        return ResponseEntity.ok(response);
    }
}