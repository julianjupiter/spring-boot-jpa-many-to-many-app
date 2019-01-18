package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.domain.Language;
import io.github.julianjupiter.springbootmanytomany.domain.Layer;
import io.github.julianjupiter.springbootmanytomany.repository.LanguageRepository;
import io.github.julianjupiter.springbootmanytomany.repository.LayerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LanguageServiceImpl implements LanguageService {
    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Iterable<Language> findAll() {
        return languageRepository.findAll();
    }

    @Override
    public Optional<Language> findById(Long id) {
        return languageRepository.findById(id);
    }

    @Override
    public void save(Language language) {
        languageRepository.save(language);
    }
}
