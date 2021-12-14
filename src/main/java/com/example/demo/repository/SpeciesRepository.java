package com.example.demo.repository;

import com.example.demo.domain.Species;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpeciesRepository extends JpaRepository<Species, Long> {

    Optional<Species> findByName(String name);
}
