package com.movie.cinereservemoviereservationbackendsystem.showtime.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeAvailabilityResponse;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeRequest;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeResponse;
import com.movie.cinereservemoviereservationbackendsystem.showtime.service.ShowtimeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/showtimes")
@RequiredArgsConstructor
public class ShowtimeController {

    private final ShowtimeService showtimeService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<ShowtimeResponse> createShowtime(@RequestBody ShowtimeRequest request) {
        return new ResponseEntity<>(showtimeService.createShowtime(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<ShowtimeResponse> updateShowtime(@PathVariable Long id, @Valid @RequestBody ShowtimeRequest request) {
        return ResponseEntity.ok(showtimeService.updateShowtime(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<Void> deleteShowtime(@PathVariable Long id) {
        showtimeService.deleteShowtime(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowtimeResponse> getShowtimeById(@PathVariable Long id) {
        return ResponseEntity.ok(showtimeService.getShowtimeById(id));
    }

    @GetMapping("/date")
    public ResponseEntity<ShowtimeAvailabilityResponse> getShowtimesByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(showtimeService.getShowtimesByDate(date));
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<ShowtimeResponse>> getUpcomingShowtimesForMovie(@PathVariable Long movieId) {
        return ResponseEntity.ok(showtimeService.getUpcomingShowtimesForMovie(movieId));
    }
}