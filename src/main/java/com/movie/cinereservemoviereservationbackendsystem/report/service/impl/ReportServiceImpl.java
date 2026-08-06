package com.movie.cinereservemoviereservationbackendsystem.report.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.auth.enums.ReservationStatus;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import com.movie.cinereservemoviereservationbackendsystem.report.api.dto.OccupancyReportResponse;
import com.movie.cinereservemoviereservationbackendsystem.report.api.dto.RevenueReportResponse;
import com.movie.cinereservemoviereservationbackendsystem.report.service.ReportService;
import com.movie.cinereservemoviereservationbackendsystem.reservation.model.Reservation;
import com.movie.cinereservemoviereservationbackendsystem.reservation.repository.ReservationRepository;
import com.movie.cinereservemoviereservationbackendsystem.showtime.model.Showtime;
import com.movie.cinereservemoviereservationbackendsystem.showtime.repository.ShowtimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final ReservationRepository reservationRepository;
    private final ShowtimeRepository showtimeRepository;

    @Override
    public RevenueReportResponse getRevenueReport(LocalDate startDate, LocalDate endDate) {
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        // Fetch all reservations (or filter via a query, iterating/filtering confirmed ones here)
        List<Reservation> reservations = reservationRepository.findAll();

        double totalRevenue = reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED || r.getStatus() == ReservationStatus.COMPLETED)
                .filter(r -> !r.getReservationTime().isBefore(startDateTime) && !r.getReservationTime().isAfter(endDateTime))
                .mapToDouble(Reservation::getTotalPrice)
                .sum();

        long totalReservations = reservations.stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED || r.getStatus() == ReservationStatus.COMPLETED)
                .filter(r -> !r.getReservationTime().isBefore(startDateTime) && !r.getReservationTime().isAfter(endDateTime))
                .count();

        return RevenueReportResponse.builder()
                .startDate(startDate)
                .endDate(endDate)
                .totalRevenue(totalRevenue)
                .totalReservations(totalReservations)
                .build();
    }

    @Override
    public OccupancyReportResponse getOccupancyReport(Long showtimeId) {
        Showtime showtime = showtimeRepository.findById(showtimeId)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with ID: " + showtimeId));

        // Get total capacity from the auditorium assigned to the showtime
        long totalSeats = showtime.getAuditorium().getCapacity();

        // Calculate booked seats by checking confirmed/completed reservations for this showtime
        long bookedSeats = reservationRepository.findAll().stream()
                .filter(r -> r.getShowtimeId().equals(showtimeId))
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED || r.getStatus() == ReservationStatus.COMPLETED)
                .mapToLong(r -> r.getReservationSeats() != null ? r.getReservationSeats().size() : 0)
                .sum();

        double occupancyPercentage = totalSeats > 0 ? ((double) bookedSeats / totalSeats) * 100.0 : 0.0;

        return OccupancyReportResponse.builder()
                .showtimeId(showtimeId)
                .totalSeats(totalSeats)
                .bookedSeats(bookedSeats)
                .occupancyPercentage(occupancyPercentage)
                .build();
    }
}