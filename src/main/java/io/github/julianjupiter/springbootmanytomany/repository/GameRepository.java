package io.github.julianjupiter.springbootmanytomany.repository;

import io.github.julianjupiter.springbootmanytomany.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
