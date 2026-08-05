package com.movie.cinereservemoviereservationbackendsystem.auth.controller;

import com.movie.cinereservemoviereservationbackendsystem.auth.dto.LoginRequest;
import com.movie.cinereservemoviereservationbackendsystem.auth.dto.LoginResponse;
import com.movie.cinereservemoviereservationbackendsystem.auth.dto.RegisterRequest;
import com.movie.cinereservemoviereservationbackendsystem.auth.service.AuthService;
import com.movie.cinereservemoviereservationbackendsystem.user.api.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        UserResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
        authService.logout(token);
        return ResponseEntity.noContent().build();
    }
}