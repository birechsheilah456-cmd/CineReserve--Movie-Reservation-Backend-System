package com.movie.cinereservemoviereservationbackendsystem.user.repository;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.Role;
import com.movie.cinereservemoviereservationbackendsystem.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByRole(Role role);
}
