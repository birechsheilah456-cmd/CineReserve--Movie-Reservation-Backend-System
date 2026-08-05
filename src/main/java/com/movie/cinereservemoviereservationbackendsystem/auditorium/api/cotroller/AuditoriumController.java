package com.movie.cinereservemoviereservationbackendsystem.auditorium.api.cotroller;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto.AuditoriumRequest;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto.AuditoriumResponse;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.service.AuditoriumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auditoriums")
@RequiredArgsConstructor
public class AuditoriumController {

    private final AuditoriumService auditoriumService;

    @PostMapping
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AuditoriumResponse> createAuditorium(@RequestBody AuditoriumRequest request) {
        AuditoriumResponse response = auditoriumService.createAuditorium(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<AuditoriumResponse> updateAuditorium(@PathVariable Long id, @RequestBody AuditoriumRequest request) {
        AuditoriumResponse response = auditoriumService.updateAuditorium(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> deleteAuditorium(@PathVariable Long id) {
        auditoriumService.deleteAuditorium(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuditoriumResponse> getAuditoriumById(@PathVariable Long id) {
        AuditoriumResponse response = auditoriumService.getAuditoriumById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<AuditoriumResponse>> getAllAuditoriums() {
        List<AuditoriumResponse> response = auditoriumService.getAllAuditoriums();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cinema/{cinemaId}")
    public ResponseEntity<List<AuditoriumResponse>> getAuditoriumsByCinema(@PathVariable Long cinemaId) {
        List<AuditoriumResponse> response = auditoriumService.getAuditoriumsByCinema(cinemaId);
        return ResponseEntity.ok(response);
    }
}