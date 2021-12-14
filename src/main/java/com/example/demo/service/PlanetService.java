package com.example.demo.service;

import com.example.demo.domain.Planet;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlanetService {

    Planet save(Planet planet);

    Optional<Planet> partialUpdate(Planet planet);

    Page<Planet> findAll(Pageable pageable);

    Optional<Planet> findOne(Long id);

    void checkByName(String name);

    void delete(Long id);
}
