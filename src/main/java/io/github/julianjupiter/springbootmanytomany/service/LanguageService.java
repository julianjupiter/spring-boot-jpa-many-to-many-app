package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.domain.Language;

import java.util.Optional;

public interface LanguageService {
    Iterable<Language> findAll();
    Optional<Language> findById(Long id);
    void save(Language language);
}
