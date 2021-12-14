package com.example.demo.web.rest;


import com.example.demo.facade.PlanetFacade;
import com.example.demo.facade.dto.PlanetDTO;
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
public class PlanetResource {

    @Value("${properties.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "planet";

    private final PlanetFacade facade;

    public PlanetResource(PlanetFacade facade) {
        this.facade = facade;
    }


    @PostMapping("/planets")
    public ResponseEntity<PlanetDTO> createPlanet(@RequestBody PlanetDTO planetDTO) throws URISyntaxException {
        if (planetDTO.getId() != null) {
            throw new BadRequestAlertException("A new planet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlanetDTO result = facade.save(planetDTO);
        return ResponseEntity
                .created(new URI("/api/planets/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/planets/{id}")
    public ResponseEntity<PlanetDTO> partialUpdatePlanet(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody PlanetDTO planetDTO
    ) {
        if (planetDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planetDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        Optional<PlanetDTO> result = facade.partialUpdate(planetDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(result.orElseThrow());
    }


    @GetMapping("/planets")
    public ResponseEntity<Page<PlanetDTO>> getAllPlanets(Pageable pageable) {
        Page<PlanetDTO> page = facade.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/planets/{id}")
    public ResponseEntity<PlanetDTO> getPlanet(@PathVariable Long id) {
        var planetDTO = facade.findOne(id);
        return ResponseEntity.ok().body(planetDTO);
    }

//I did the delete method, because it was a study, but within the context it doesn't make sense and it wouldn't be called
    @DeleteMapping("/planets/{id}")
    public ResponseEntity<Void> deletePlanet(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
