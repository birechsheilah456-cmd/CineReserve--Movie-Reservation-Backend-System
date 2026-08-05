package com.movie.cinereservemoviereservationbackendsystem.auth.dto;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String token;
    private String tokenType;
    private long expiresInMs;
    private Long userId;
    private String fullName;
    private String email;
    private Role role;
}