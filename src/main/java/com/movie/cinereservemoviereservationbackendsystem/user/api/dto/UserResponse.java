package com.movie.cinereservemoviereservationbackendsystem.user.api.dto;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.Role;
import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String fullName,
        String email,
        Role role,
        boolean active,
        int tokenVersion,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}