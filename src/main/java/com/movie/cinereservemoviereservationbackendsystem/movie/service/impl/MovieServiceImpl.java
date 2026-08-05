package com.movie.cinereservemoviereservationbackendsystem.movie.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.common.exception.BusinessRuleViolationException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.DuplicateResourceException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import com.movie.cinereservemoviereservationbackendsystem.genre.model.Genre;
import com.movie.cinereservemoviereservationbackendsystem.genre.repository.GenreRepository;
import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieRequest;
import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieResponse;
import com.movie.cinereservemoviereservationbackendsystem.movie.api.dto.MovieSummaryResponse;
import com.movie.cinereservemoviereservationbackendsystem.movie.model.Movie;
import com.movie.cinereservemoviereservationbackendsystem.movie.repository.MovieRepository;
import com.movie.cinereservemoviereservationbackendsystem.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public MovieResponse createMovie(MovieRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Movie title cannot be empty.");
        }
        if (movieRepository.findByTitle(request.getTitle()).isPresent()) {
            throw new DuplicateResourceException("Movie with title '" + request.getTitle() + "' already exists.");
        }

        Genre genre = genreRepository.findById(request.getGenreId())
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + request.getGenreId()));

        Movie movie = Movie.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .posterImage(request.getPosterImage())
                .duration(request.getDuration())
                .genre(genre)
                .build();

        Movie savedMovie = movieRepository.save(movie);
        return mapToResponse(savedMovie);
    }

    @Override
    @Transactional
    public MovieResponse updateMovie(Long id, MovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));

        if (request.getTitle() != null && !request.getTitle().trim().isEmpty()) {
            movie.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            movie.setDescription(request.getDescription());
        }
        if (request.getPosterImage() != null) {
            movie.setPosterImage(request.getPosterImage());
        }
        if (request.getDuration() != null) {
            movie.setDuration(request.getDuration());
        }
        if (request.getGenreId() != null) {
            Genre genre = genreRepository.findById(request.getGenreId())
                    .orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + request.getGenreId()));
            movie.setGenre(genre);
        }

        Movie updatedMovie = movieRepository.save(movie);
        return mapToResponse(updatedMovie);
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));

        // TODO: Add check here once the showtime module is created to enforce:
        // "A movie with future reservations cannot be deleted."

        movieRepository.delete(movie);
    }

    @Override
    public MovieResponse getMovieById(Long id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not found with ID: " + id));
        return mapToResponse(movie);
    }

    @Override
    public List<MovieSummaryResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieSummaryResponse> getMoviesByGenre(Long genreId) {
        return movieRepository.findByGenreId(genreId).stream()
                .map(this::mapToSummaryResponse)
                .collect(Collectors.toList());
    }

    private MovieResponse mapToResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .posterImage(movie.getPosterImage())
                .duration(movie.getDuration())
                .genreName(movie.getGenre() != null ? movie.getGenre().getName() : null)
                .build();
    }

    private MovieSummaryResponse mapToSummaryResponse(Movie movie) {
        return MovieSummaryResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .posterImage(movie.getPosterImage())
                .duration(movie.getDuration())
                .genreName(movie.getGenre() != null ? movie.getGenre().getName() : null)
                .build();
    }
}