package com.example.demo.web.rest;


import com.example.demo.facade.KnownMastersFacade;
import com.example.demo.facade.dto.KnownMasterDTO;
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
public class KnownMasterResource {

    @Value("${properties.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "knownMasters";

    private final KnownMastersFacade facade;

    public KnownMasterResource(KnownMastersFacade facade) {
        this.facade = facade;
    }


    @PostMapping("/known-masters")
    public ResponseEntity<KnownMasterDTO> createKnownMaster(@RequestBody KnownMasterDTO knownMasterDTO) throws URISyntaxException {
        if (knownMasterDTO.getId() != null) {
            throw new BadRequestAlertException("A new KnownMaster cannot already have an ID", ENTITY_NAME, "idexists");
        }
        KnownMasterDTO result = facade.save(knownMasterDTO);
        return ResponseEntity
                .created(new URI("/api/known-masters/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/known-masters/{id}")
    public ResponseEntity<KnownMasterDTO> partialUpdateKnownMaster(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody KnownMasterDTO knownMasterDTO
    ) {
        if (knownMasterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, knownMasterDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        Optional<KnownMasterDTO> result = facade.partialUpdate(knownMasterDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(result.orElseThrow());
    }


    @GetMapping("/known-masters")
    public ResponseEntity<Page<KnownMasterDTO>> getAllKnownMasters(Pageable pageable) {
        Page<KnownMasterDTO> page = facade.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/known-masters/{id}")
    public ResponseEntity<KnownMasterDTO> getKnownMaster(@PathVariable Long id) {
        var knownMastersDTO = facade.findOne(id);
        return ResponseEntity.ok().body(knownMastersDTO);
    }

    @DeleteMapping("/known-masters/{id}")
    public ResponseEntity<Void> deleteKnownMaster(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
