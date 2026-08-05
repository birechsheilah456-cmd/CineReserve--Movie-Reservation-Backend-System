package com.movie.cinereservemoviereservationbackendsystem.auth.dto;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.Role;

public record RegisterRequest(
        String fullName,
        String email,
        String password,
        Role role
) {}