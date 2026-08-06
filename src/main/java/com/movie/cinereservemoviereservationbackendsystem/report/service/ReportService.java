package com.movie.cinereservemoviereservationbackendsystem.report.service;

import com.movie.cinereservemoviereservationbackendsystem.report.api.dto.OccupancyReportResponse;
import com.movie.cinereservemoviereservationbackendsystem.report.api.dto.RevenueReportResponse;

import java.time.LocalDate;

public interface ReportService {
    RevenueReportResponse getRevenueReport(LocalDate startDate, LocalDate endDate);
    OccupancyReportResponse getOccupancyReport(Long showtimeId);
}