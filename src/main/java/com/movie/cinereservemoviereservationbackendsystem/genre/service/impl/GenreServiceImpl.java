package com.movie.cinereservemoviereservationbackendsystem.genre.service.impl;

import com.movie.cinereservemoviereservationbackendsystem.common.exception.BusinessRuleViolationException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.DuplicateResourceException;
import com.movie.cinereservemoviereservationbackendsystem.common.exception.ResourceNotFoundException;
import com.movie.cinereservemoviereservationbackendsystem.genre.api.dto.GenreRequest;
import com.movie.cinereservemoviereservationbackendsystem.genre.api.dto.GenreResponse;
import com.movie.cinereservemoviereservationbackendsystem.genre.model.Genre;
import com.movie.cinereservemoviereservationbackendsystem.genre.repository.GenreRepository;
import com.movie.cinereservemoviereservationbackendsystem.genre.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    @Transactional
    public GenreResponse createGenre(GenreRequest request) {
        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new BusinessRuleViolationException("Genre name cannot be empty.");
        }

        if (genreRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateResourceException("Genre with name '" + request.getName() + "' already exists.");
        }

        Genre genre = Genre.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        Genre savedGenre = genreRepository.save(genre);
        return mapToResponse(savedGenre);
    }

    @Override
    @Transactional
    public GenreResponse updateGenre(Long id, GenreRequest request) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + id));

        if (request.getName() != null && !request.getName().trim().isEmpty()) {
            genre.setName(request.getName());
        }
        if (request.getDescription() != null) {
            genre.setDescription(request.getDescription());
        }

        Genre updatedGenre = genreRepository.save(genre);
        return mapToResponse(updatedGenre);
    }

    @Override
    @Transactional
    public void deleteGenre(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + id));

        // Optional: Check if any movies are currently linked to this genre before deleting
        genreRepository.delete(genre);
    }

    @Override
    public GenreResponse getGenreById(Long id) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with ID: " + id));
        return mapToResponse(genre);
    }

    @Override
    public List<GenreResponse> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private GenreResponse mapToResponse(Genre genre) {
        return GenreResponse.builder()
                .id(genre.getId())
                .name(genre.getName())
                .description(genre.getDescription())
                .build();
    }
}