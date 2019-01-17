package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.domain.Layer;

import java.util.Optional;

public interface LayerService {
    Iterable<Layer> findAll();
    Optional<Layer> findById(Long id);
    void save(Layer layer);
}
