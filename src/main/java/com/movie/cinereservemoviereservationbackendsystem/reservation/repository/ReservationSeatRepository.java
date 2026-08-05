package com.movie.cinereservemoviereservationbackendsystem.reservation.repository;

import com.movie.cinereservemoviereservationbackendsystem.reservation.model.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {
}