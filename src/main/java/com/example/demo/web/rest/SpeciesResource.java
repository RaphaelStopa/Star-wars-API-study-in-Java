package com.example.demo.web.rest;


import com.example.demo.facade.SpeciesFacade;
import com.example.demo.facade.dto.SpeciesDTO;
import com.example.demo.service.exceptions.BadRequestAlertException;
import com.example.demo.util.HeaderUtil;
import com.example.demo.util.PaginationUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class SpeciesResource {

    @Value("${properties.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "species";

    private final SpeciesFacade facade;

    public SpeciesResource(SpeciesFacade facade) {
        this.facade = facade;
    }


    @PostMapping("/species")
    public ResponseEntity<SpeciesDTO> createSpecies(@RequestBody SpeciesDTO speciesDTO) throws URISyntaxException {
        if (speciesDTO.getId() != null) {
            throw new BadRequestAlertException("A new Species cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SpeciesDTO result = facade.save(speciesDTO);
        return ResponseEntity
                .created(new URI("/api/species/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/species/{id}")
    public ResponseEntity<SpeciesDTO> partialUpdateSpecies(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody SpeciesDTO speciesDTO
    ) {
        if (speciesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, speciesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        Optional<SpeciesDTO> result = facade.partialUpdate(speciesDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(result.orElseThrow());
    }


    @GetMapping("/species")
    public ResponseEntity<Page<SpeciesDTO>> getAllSpecies(Pageable pageable) {
        Page<SpeciesDTO> page = facade.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/species/{id}")
    public ResponseEntity<SpeciesDTO> getSpecies(@PathVariable Long id) {
        var planetDTO = facade.findOne(id);
        return ResponseEntity.ok().body(planetDTO);
    }

    //I did the delete method, because it was a study, but within the context it doesn't make sense and it wouldn't be called
    @DeleteMapping("/species/{id}")
    public ResponseEntity<Void> deleteSpecies(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
