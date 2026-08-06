package com.movie.cinereservemoviereservationbackendsystem.validation;

import com.movie.cinereservemoviereservationbackendsystem.movie.repository.MovieRepository;
import com.movie.cinereservemoviereservationbackendsystem.showtime.api.dto.ShowtimeRequest;
import com.movie.cinereservemoviereservationbackendsystem.showtime.repository.ShowtimeRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NoScheduleConflictValidator implements ConstraintValidator<NoScheduleConflict, ShowtimeRequest> {

    private final ShowtimeRepository showtimeRepository;
    private final MovieRepository movieRepository;

    @Override
    public boolean isValid(ShowtimeRequest request, ConstraintValidatorContext context) {
        if (request == null || request.getAuditoriumId() == null || request.getStartTime() == null || request.getMovieId() == null) {
            return true; // Defer missing field handling to @NotNull annotations
        }

        LocalDateTime startTime = request.getStartTime();

        // Fetch movie duration (using getDuration() matching your Movie entity / MovieService)
        Integer durationMinutes = movieRepository.findById(request.getMovieId())
                .map(movie -> movie.getDuration())
                .orElse(120); // Fallback default to 120 minutes if movie not found

        LocalDateTime endTime = startTime.plusMinutes(durationMinutes);

        boolean hasConflict = showtimeRepository.existsOverlappingShowtime(
                request.getAuditoriumId(),
                startTime,
                endTime
        );

        return !hasConflict;
    }
}