package com.movie.cinereservemoviereservationbackendsystem.report.api.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RevenueReportResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalRevenue;
    private Long totalReservations;
}