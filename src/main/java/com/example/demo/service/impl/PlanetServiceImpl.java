package com.example.demo.service.impl;

import com.example.demo.domain.Planet;
import com.example.demo.repository.PlanetRepository;
import com.example.demo.service.PlanetService;
import com.example.demo.service.exceptions.BadRequestAlertException;
import com.example.demo.service.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PlanetServiceImpl implements PlanetService {

    private static final String ENTITY_NAME = "planet";

    private final PlanetRepository repository;


    public PlanetServiceImpl(PlanetRepository repository) {
        this.repository = repository;
    }


    @Override
    public Planet save(Planet planet) {

        checkByName(planet.getName());

        if(planet.getId() == null) {

            if(repository.existsByName(planet.getName())) {
                throw new BadRequestAlertException("already exists with this name", ENTITY_NAME, "repeated name");
            }


        }
        // TODO: validation is missing for update if there is no name
        return repository.save(planet);
    }


    @Override
    public Page<Planet> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Planet> findOne(@NonNull Long id) {
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
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
