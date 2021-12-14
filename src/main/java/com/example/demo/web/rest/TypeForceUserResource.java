package com.example.demo.web.rest;


import com.example.demo.facade.TypeForceUserFacade;
import com.example.demo.facade.dto.TypeForceUserDTO;
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
public class TypeForceUserResource {


    @Value("${properties.clientApp.name}")
    private String applicationName;

    private static final String ENTITY_NAME = "typeForceUser";

    private final TypeForceUserFacade facade;

    public TypeForceUserResource(TypeForceUserFacade facade) {
        this.facade = facade;
    }

    @PostMapping("/type-users")
    public ResponseEntity<TypeForceUserDTO> createTypeForceUser(@RequestBody TypeForceUserDTO typeForceUserDTO) throws URISyntaxException {
        if (typeForceUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new TypeForceUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TypeForceUserDTO result = facade.save(typeForceUserDTO);
        return ResponseEntity
                .created(new URI("/api/type-users/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    @PatchMapping(value = "/type-users/{id}")
    public ResponseEntity<TypeForceUserDTO> partialUpdateTypeForceUser(
            @PathVariable(value = "id", required = false) final Long id,
            @RequestBody TypeForceUserDTO typeForceUserDTO
    ) {
        if (typeForceUserDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, typeForceUserDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        Optional<TypeForceUserDTO> result = facade.partialUpdate(typeForceUserDTO);
        return ResponseEntity
                .ok()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).body(result.orElseThrow());
    }


    @GetMapping("/type-users")
    public ResponseEntity<Page<TypeForceUserDTO>> getAllTypeForceUsers(Pageable pageable) {
        Page<TypeForceUserDTO> page = facade.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page);
    }


    @GetMapping("/type-users/{id}")
    public ResponseEntity<TypeForceUserDTO> getTypeForceUser(@PathVariable Long id) {
        var typeForceUserDTO = facade.findOne(id);
        return ResponseEntity.ok().body(typeForceUserDTO);
    }


    @DeleteMapping("/type-users/{id}")
    public ResponseEntity<Void> deleteTypeForceUser(@PathVariable Long id) {
        facade.delete(id);
        return ResponseEntity
                .noContent()
                .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
                .build();
    }
}
