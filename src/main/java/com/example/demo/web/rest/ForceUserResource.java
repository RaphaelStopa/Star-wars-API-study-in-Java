package com.example.demo.web.rest;


import com.example.demo.facade.ForceUserFacade;
import com.example.demo.facade.dto.ForceUserDTO;
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
public class ForceUserResource {

    @Value("${properties.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "forceUser";

    private final ForceUserFacade facade;

    public ForceUserResource(ForceUserFacade facade) {
        this.facade = facade;
    }


    @PostMapping("/force-users")
    public ResponseEntity<ForceUserDTO> createForceUser(@RequestBody ForceUserDTO forceUserDTO) throws URISyntaxException {
        if (forceUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new ForceUse cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ForceUserDTO result = facade.save(forceUserDTO);
        return ResponseEntity
                .created(new URI("/api/force-users/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/force-users/{id}")
    public ResponseEntity<ForceUserDTO> partialUpdateForceUser(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody ForceUserDTO  forceUserDTO
    ) {
        if (forceUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, forceUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        Optional<ForceUserDTO> result = facade.partialUpdate(forceUserDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(result.orElseThrow());
    }


    @GetMapping("/force-users")
    public ResponseEntity<Page<ForceUserDTO>> getAllPlanets(Pageable pageable) {
        Page<ForceUserDTO> page = facade.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/force-users/{id}")
    public ResponseEntity<ForceUserDTO> getPlanet(@PathVariable Long id) {
        var forceUserDTO = facade.findOne(id);
        return ResponseEntity.ok().body(forceUserDTO);
    }

    @DeleteMapping("/force-users/{id}")
    public ResponseEntity<Void> deleteForceUser(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
