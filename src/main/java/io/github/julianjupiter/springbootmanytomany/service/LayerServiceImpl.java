package io.github.julianjupiter.springbootmanytomany.service;

import io.github.julianjupiter.springbootmanytomany.domain.Layer;
import io.github.julianjupiter.springbootmanytomany.repository.LayerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class LayerServiceImpl implements LayerService {
    private final LayerRepository layerRepository;

    public LayerServiceImpl(LayerRepository layerRepository) {
        this.layerRepository = layerRepository;
    }

    @Override
    public Iterable<Layer> findAll() {
        return layerRepository.findAll();
    }

    @Override
    public Optional<Layer> findById(Long id) {
        return layerRepository.findById(id);
    }

    @Override
    public void save(Layer layer) {
        layerRepository.save(layer);
    }
}
