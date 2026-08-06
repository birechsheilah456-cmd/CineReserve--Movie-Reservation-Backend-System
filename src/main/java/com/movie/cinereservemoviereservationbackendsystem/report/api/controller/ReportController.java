package com.movie.cinereservemoviereservationbackendsystem.report.api.controller;

import com.movie.cinereservemoviereservationbackendsystem.report.api.dto.OccupancyReportResponse;
import com.movie.cinereservemoviereservationbackendsystem.report.api.dto.RevenueReportResponse;
import com.movie.cinereservemoviereservationbackendsystem.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/revenue")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<RevenueReportResponse> getRevenueReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        RevenueReportResponse response = reportService.getRevenueReport(startDate, endDate);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/occupancy/{showtimeId}")
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'CINEMA_MANAGER')")
    public ResponseEntity<OccupancyReportResponse> getOccupancyReport(@PathVariable Long showtimeId) {
        OccupancyReportResponse response = reportService.getOccupancyReport(showtimeId);
        return ResponseEntity.ok(response);
    }
}