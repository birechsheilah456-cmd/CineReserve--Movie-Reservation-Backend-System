package com.movie.cinereservemoviereservationbackendsystem.auditorium.repository;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.model.Auditorium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    List<Auditorium> findByCinemaId(Long cinemaId);
    boolean existsByNameAndCinemaId(String name, Long cinemaId);
}