package com.movie.cinereservemoviereservationbackendsystem.showtime.repository;

import com.movie.cinereservemoviereservationbackendsystem.showtime.model.Showtime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShowtimeRepository extends JpaRepository<Showtime, Long> {

    @Query("SELECT s FROM Showtime s WHERE s.auditorium.id = :auditoriumId " +
            "AND (:excludeId IS NULL OR s.id <> :excludeId) " +
            "AND s.startTime < :endTime AND s.endTime > :startTime")
    List<Showtime> findConflictingShowtimes(
            @Param("auditoriumId") Long auditoriumId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("excludeId") Long excludeId
    );

    @Query("SELECT COUNT(s) > 0 FROM Showtime s WHERE s.auditorium.id = :auditoriumId " +
            "AND s.startTime < :endTime AND s.endTime > :startTime")
    boolean existsOverlappingShowtime(
            @Param("auditoriumId") Long auditoriumId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime
    );

    List<Showtime> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    List<Showtime> findByMovieIdAndStartTimeAfter(Long movieId, LocalDateTime now);
}