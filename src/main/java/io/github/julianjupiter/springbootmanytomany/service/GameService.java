package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.domain.Game;

import java.util.Optional;

public interface GameService {
    Iterable<Game> findAll();
    Optional<Game> findById(Long id);
    void save(Game game);
}
