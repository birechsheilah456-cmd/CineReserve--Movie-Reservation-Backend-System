package com.movie.cinereservemoviereservationbackendsystem.report.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OccupancyReportResponse {
    private Long showtimeId;
    private Long totalSeats;
    private Long bookedSeats;
    private Double occupancyPercentage;
}