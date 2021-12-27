package com.example.demo.service;

import com.example.demo.domain.Species;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SpeciesService {

    Species save(Species species);

    Page<Species> findAll(Pageable pageable);

    Optional<Species> findOne(Long id);

    void checkByName(String name);

    void delete(Long id);
}
