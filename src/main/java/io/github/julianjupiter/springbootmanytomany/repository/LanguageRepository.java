package io.github.julianjupiter.springbootmanytomany.repository;

import io.github.julianjupiter.springbootmanytomany.domain.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
