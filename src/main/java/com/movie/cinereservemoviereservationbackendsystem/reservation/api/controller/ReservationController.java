package com.movie.cinereservemoviereservationbackendsystem.reservation.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto.ReservationRequest;
import com.movie.cinereservemoviereservationbackendsystem.reservation.api.dto.ReservationResponse;
import com.movie.cinereservemoviereservationbackendsystem.reservation.service.ReservationService;
import com.movie.cinereservemoviereservationbackendsystem.user.model.User;
import com.movie.cinereservemoviereservationbackendsystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final UserRepository userRepository;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationResponse> createReservation(
            @AuthenticationPrincipal String email, // JwtAuthFilter sets the email string as the principal
            @RequestBody ReservationRequest request) {

        Long userId = extractUserIdFromEmail(email);
        ReservationResponse response = reservationService.createReservation(userId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationResponse> getReservationById(@PathVariable Long id) {
        ReservationResponse response = reservationService.getReservationById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/user/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReservationResponse>> getMyReservations(@AuthenticationPrincipal String email) {
        Long userId = extractUserIdFromEmail(email);
        List<ReservationResponse> response = reservationService.getUserReservations(userId);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReservationResponse> cancelReservation(@PathVariable Long id) {
        ReservationResponse response = reservationService.cancelReservation(id);
        return ResponseEntity.ok(response);
    }

    private Long extractUserIdFromEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResourceNotFoundException("Authenticated user email not found in security context.");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
        return user.getId();
    }
}