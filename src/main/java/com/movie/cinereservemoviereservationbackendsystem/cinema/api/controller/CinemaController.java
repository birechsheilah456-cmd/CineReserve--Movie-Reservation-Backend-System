package com.movie.cinereservemoviereservationbackendsystem.cinema.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto.CinemaRequest;
import com.movie.cinereservemoviereservationbackendsystem.cinema.api.dto.CinemaResponse;
import com.movie.cinereservemoviereservationbackendsystem.cinema.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<CinemaResponse> createCinema(@RequestBody CinemaRequest request) {
        CinemaResponse response = cinemaService.createCinema(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<CinemaResponse> updateCinema(@PathVariable Long id, @RequestBody CinemaRequest request) {
        CinemaResponse response = cinemaService.updateCinema(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteCinema(@PathVariable Long id) {
        cinemaService.deleteCinema(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CinemaResponse> getCinemaById(@PathVariable Long id) {
        CinemaResponse response = cinemaService.getCinemaById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CinemaResponse>> getAllCinemas() {
        List<CinemaResponse> response = cinemaService.getAllCinemas();
        return ResponseEntity.ok(response);
    }
}