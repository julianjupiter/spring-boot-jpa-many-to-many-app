package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.domain.Genre;

import java.util.Optional;

public interface GenreService {
    Iterable<Genre> findAll();
    Optional<Genre> findById(Long id);
    void save(Genre genre);
}
