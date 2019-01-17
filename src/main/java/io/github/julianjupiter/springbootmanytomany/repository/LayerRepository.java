package io.github.julianjupiter.springbootmanytomany.repository;

import io.github.julianjupiter.springbootmanytomany.domain.Layer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LayerRepository extends JpaRepository<Layer, Long> {
}
