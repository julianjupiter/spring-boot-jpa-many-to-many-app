package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.domain.Genre;
import io.github.julianjupiter.springbootmanytomany.repository.GenreRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    @Override
    public Iterable<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public void save(Genre genre) {
        genreRepository.save(genre);
    }
}
