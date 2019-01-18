package io.github.julianjupiter.springbootmanytomany.repository;

import io.github.julianjupiter.springbootmanytomany.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}
