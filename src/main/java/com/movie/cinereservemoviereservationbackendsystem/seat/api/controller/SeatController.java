package com.movie.cinereservemoviereservationbackendsystem.seat.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatAvailabilityResponse;
import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatRequest;
import com.movie.cinereservemoviereservationbackendsystem.seat.api.dto.SeatResponse;
import com.movie.cinereservemoviereservationbackendsystem.seat.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<SeatResponse> createSeat(@RequestBody SeatRequest request) {
        SeatResponse response = seatService.createSeat(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<SeatResponse> updateSeat(
            @PathVariable Long id,
            @RequestBody SeatRequest request) {

        SeatResponse response = seatService.updateSeat(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatResponse> getSeatById(@PathVariable Long id) {
        SeatResponse response = seatService.getSeatById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SeatResponse>> getAllSeats() {
        List<SeatResponse> response = seatService.getAllSeats();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/auditorium/{auditoriumId}")
    public ResponseEntity<List<SeatAvailabilityResponse>> getSeatsByAuditorium(
            @PathVariable Long auditoriumId) {

        List<SeatAvailabilityResponse> response =
                seatService.getSeatsByAuditorium(auditoriumId);

        return ResponseEntity.ok(response);
    }
}