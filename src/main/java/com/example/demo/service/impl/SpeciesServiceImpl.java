package com.example.demo.service.impl;

import com.example.demo.domain.Species;
import com.example.demo.repository.SpeciesRepository;
import com.example.demo.service.SpeciesService;
import com.example.demo.service.exceptions.BadRequestAlertException;
import com.example.demo.service.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SpeciesServiceImpl implements SpeciesService {

    private static final String ENTITY_NAME = "species";

    private final SpeciesRepository repository;

    public SpeciesServiceImpl(SpeciesRepository repository) {
        this.repository = repository;
    }

    @Override
    public Species save(Species species) {
        return repository.save(species);
    }

    @Override
    public Optional<Species> partialUpdate(Species species) {
        if (!repository.existsById(species.getId())) {
            throw BusinessException.badRequest();
        }
        return Optional.of(repository.save(species));
    }

    @Override
    public Page<Species> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Species> findOne(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public void checkByName(String name) {

        if (Optional.ofNullable(name).isEmpty()) {
            throw new BadRequestAlertException("need a name", ENTITY_NAME, "nonexistent name");
        }

        if (name.isBlank()) {
            throw new BadRequestAlertException("need a name", ENTITY_NAME, "nonexistent name");
        }

        if (!repository.findByName(name).isEmpty()) {
            throw new BadRequestAlertException("already exists with this name", ENTITY_NAME, "repeated name");
        }
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
