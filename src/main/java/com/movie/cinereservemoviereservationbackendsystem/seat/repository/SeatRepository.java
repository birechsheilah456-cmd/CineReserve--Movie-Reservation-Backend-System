package com.movie.cinereservemoviereservationbackendsystem.seat.repository;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.model.Auditorium;
import com.movie.cinereservemoviereservationbackendsystem.seat.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    Optional<Seat> findBySeatNumberAndAuditorium(String seatNumber, Auditorium auditorium);

    List<Seat> findByAuditoriumId(Long auditoriumId);
}