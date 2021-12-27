package com.example.demo.repository;

import com.example.demo.domain.Planet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long> {

    List<Planet> findAllByName(String name);

    boolean existsByName(String name);

}
