package com.movie.cinereservemoviereservationbackendsystem.user.api.dto;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.Role;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRoleRequest(
        @NotNull(message = "Role is required")
        Role role
) {}