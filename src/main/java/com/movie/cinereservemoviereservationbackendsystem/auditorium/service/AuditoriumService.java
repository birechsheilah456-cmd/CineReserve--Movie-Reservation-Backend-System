package com.movie.cinereservemoviereservationbackendsystem.auditorium.service;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto.AuditoriumRequest;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.api.dto.AuditoriumResponse;

import java.util.List;

public interface AuditoriumService {
    AuditoriumResponse createAuditorium(AuditoriumRequest request);
    AuditoriumResponse updateAuditorium(Long id, AuditoriumRequest request);
    void deleteAuditorium(Long id);
    AuditoriumResponse getAuditoriumById(Long id);
    List<AuditoriumResponse> getAllAuditoriums();
    List<AuditoriumResponse> getAuditoriumsByCinema(Long cinemaId);
}