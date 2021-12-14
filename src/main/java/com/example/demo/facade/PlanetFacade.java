package com.example.demo.facade;

import com.example.demo.domain.Planet;
import com.example.demo.facade.dto.PlanetDTO;
import com.example.demo.facade.mapper.PlanetMapper;
import com.example.demo.service.PlanetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class PlanetFacade {

    private final Logger log = LoggerFactory.getLogger(PlanetFacade.class);

    private final PlanetMapper mapper;

    private final PlanetService service;

    public PlanetFacade(PlanetMapper mapper, PlanetService service) {
        this.mapper = mapper;
        this.service = service;
    }

    public PlanetDTO save(PlanetDTO planetDTO) {
        log.debug("Request to save Planet : {}", planetDTO);
        service.checkByName(planetDTO.getName());
        Planet planet = mapper.toEntity(planetDTO);
        planet = service.save(planet);
        return mapper.toDto(planet);
    }

    public Optional<PlanetDTO> partialUpdate(PlanetDTO planetDTO) {
        log.debug("Request to partially update Planet : {}", planetDTO);
        return service
                .findOne(planetDTO.getId())
                .map(
                        existingPlanet -> {
                            mapper.partialUpdate(existingPlanet, planetDTO);
                            return existingPlanet;
                        }
                )
                .map(service::save)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<PlanetDTO> findAll(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public PlanetDTO findOne(Long id) {
        log.debug("Request to get Planet : {}", id);
        return mapper.toDto(service.findOne(id).orElseThrow());
    }

    public void delete(Long id) {
        log.debug("Request to delete Planet : {}", id);
        service.delete(id);
    }
}
