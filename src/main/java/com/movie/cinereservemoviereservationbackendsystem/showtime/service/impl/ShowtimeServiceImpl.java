package com.movie.cinereservemoviereservationbackendsystem.showtime.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.auditorium.model.Auditorium;
import com.movie.cinereservemoviereservationbackendsystem.auditorium.repository.AuditoriumRepository;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.BusinessRuleViolationException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import com.movie.cinereservemoviereservationbackendsystem.movie.model.Movie;
import com.movie.cinereservemoviereservationbackendsystem.movie.repository.MovieRepository;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeAvailabilityResponse;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeRequest;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeResponse;
import com.movie.cinereservemoviereservationbackendsystem.showtime.model.Showtime;
import com.movie.cinereservemoviereservationbackendsystem.showtime.repository.ShowtimeRepository;
import com.movie.cinereservemoviereservationbackendsystem.showtime.service.ShowtimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShowtimeServiceImpl implements ShowtimeService {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;
    private final AuditoriumRepository auditoriumRepository;

    @Override
    @Transactional
    public ShowtimeResponse createShowtime(ShowtimeRequest request) {
        validateRequestFields(request);

        if (request.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleViolationException("Cannot create a showtime in the past.");
        }

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + request.getMovieId()));

        Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId())
                .orElseThrow(() -> new ResourceNotFoundException("Auditorium not found with ID: " + request.getAuditoriumId()));

        LocalDateTime calculatedEndTime = request.getStartTime().plusMinutes(movie.getDuration());

        validateNoScheduleConflict(auditorium.getId(), request.getStartTime(), calculatedEndTime, null);

        Showtime showtime = Showtime.builder()
                .movie(movie)
                .auditorium(auditorium)
                .startTime(request.getStartTime())
                .endTime(calculatedEndTime)
                .price(request.getPrice())
                .build();

        Showtime saved = showtimeRepository.save(showtime);
        return mapToResponse(saved);
    }

    @Override
    @Transactional
    public ShowtimeResponse updateShowtime(Long id, ShowtimeRequest request) {
        validateRequestFields(request);

        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with ID: " + id));

        if (request.getStartTime().isBefore(LocalDateTime.now())) {
            throw new BusinessRuleViolationException("Cannot set a showtime start time to the past.");
        }

        Movie movie = movieRepository.findById(request.getMovieId())
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + request.getMovieId()));

        Auditorium auditorium = auditoriumRepository.findById(request.getAuditoriumId())
                .orElseThrow(() -> new ResourceNotFoundException("Auditorium not found with ID: " + request.getAuditoriumId()));

        LocalDateTime calculatedEndTime = request.getStartTime().plusMinutes(movie.getDuration());

        validateNoScheduleConflict(auditorium.getId(), request.getStartTime(), calculatedEndTime, id);

        showtime.setMovie(movie);
        showtime.setAuditorium(auditorium);
        showtime.setStartTime(request.getStartTime());
        showtime.setEndTime(calculatedEndTime);
        showtime.setPrice(request.getPrice());

        Showtime updated = showtimeRepository.save(showtime);
        return mapToResponse(updated);
    }

    @Override
    @Transactional
    public void deleteShowtime(Long id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with ID: " + id));

        showtimeRepository.delete(showtime);
    }

    @Override
    public ShowtimeResponse getShowtimeById(Long id) {
        Showtime showtime = showtimeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Showtime not found with ID: " + id));
        return mapToResponse(showtime);
    }

    @Override
    public List<ShowtimeResponse> getAllShowtimes() {
        return showtimeRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ShowtimeAvailabilityResponse getShowtimesByDate(LocalDate date) {
        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = date.atTime(LocalTime.MAX);

        List<ShowtimeResponse> showtimes = showtimeRepository.findByStartTimeBetween(dayStart, dayEnd)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ShowtimeAvailabilityResponse.builder()
                .date(date)
                .availableShowtime(showtimes)
                .build();
    }

    @Override
    public List<ShowtimeResponse> getUpcomingShowtimesForMovie(Long movieId) {
        return showtimeRepository.findByMovieIdAndStartTimeAfter(movieId, LocalDateTime.now())
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private void validateRequestFields(ShowtimeRequest request) {
        if (request == null) {
            throw new BusinessRuleViolationException("Request body cannot be null.");
        }
        if (request.getMovieId() == null) {
            throw new BusinessRuleViolationException("Movie ID is required.");
        }
        if (request.getAuditoriumId() == null) {
            throw new BusinessRuleViolationException("Auditorium ID is required.");
        }
        if (request.getStartTime() == null) {
            throw new BusinessRuleViolationException("Start time is required.");
        }
        if (request.getPrice() == null) {
            throw new BusinessRuleViolationException("Price is required.");
        }
        if (request.getPrice() <= 0) {
            throw new BusinessRuleViolationException("Price must be greater than zero.");
        }
    }

    private void validateNoScheduleConflict(Long auditoriumId, LocalDateTime startTime, LocalDateTime endTime, Long excludeShowtimeId) {
        List<Showtime> conflicts = showtimeRepository.findConflictingShowtimes(auditoriumId, startTime, endTime, excludeShowtimeId);
        if (!conflicts.isEmpty()) {
            throw new BusinessRuleViolationException("Conflicting showtime exists in the specified auditorium for this time slot.");
        }
    }

    private ShowtimeResponse mapToResponse(Showtime showtime) {
        return ShowtimeResponse.builder()
                .id(showtime.getId())
                .movieId(showtime.getMovie().getId())
                .movieTitle(showtime.getMovie().getTitle())
                .auditoriumId(showtime.getAuditorium().getId())
                .auditoriumName(showtime.getAuditorium().getName())
                .startTime(showtime.getStartTime())
                .endTime(showtime.getEndTime())
                .price(showtime.getPrice())
                .build();
    }
}