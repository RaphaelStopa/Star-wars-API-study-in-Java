package com.example.demo.facade;

import com.example.demo.domain.Species;
import com.example.demo.facade.dto.SpeciesDTO;
import com.example.demo.facade.mapper.SpeciesMapper;
import com.example.demo.service.SpeciesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class SpeciesFacade {

    private final Logger log = LoggerFactory.getLogger(SpeciesFacade.class);

    private final SpeciesMapper mapper;

    private final SpeciesService service;

    public SpeciesFacade(SpeciesMapper mapper, SpeciesService service) {
        this.mapper = mapper;
        this.service = service;
    }

    public SpeciesDTO save(SpeciesDTO speciesDTO) {
        log.debug("Request to save Species : {}", speciesDTO);
        service.checkByName(speciesDTO.getName());
        Species species = mapper.toEntity(speciesDTO);
        species = service.save(species);
        return mapper.toDto(species);
    }

    public Optional<SpeciesDTO> partialUpdate(SpeciesDTO speciesDTO) {
        log.debug("Request to partially update Species : {}", speciesDTO);
        return service
                .findOne(speciesDTO.getId())
                .map(
                        existingSpecies -> {
                            mapper.partialUpdate(existingSpecies, speciesDTO);
                            return existingSpecies;
                        }
                )
                .map(service::save)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<SpeciesDTO> findAll(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public SpeciesDTO findOne(Long id) {
        log.debug("Request to get Species : {}", id);
        return mapper.toDto(service.findOne(id).orElseThrow());
    }

    public void delete(Long id) {
        log.debug("Request to delete Species : {}", id);
        service.delete(id);
    }
}
