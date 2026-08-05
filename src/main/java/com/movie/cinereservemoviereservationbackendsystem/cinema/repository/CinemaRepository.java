package com.movie.cinereservemoviereservationbackendsystem.cinema.repository;

import com.movie.cinereservemoviereservationbackendsystem.cinema.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    Optional<Cinema> findByName(String name);
}