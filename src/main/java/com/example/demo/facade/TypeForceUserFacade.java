package com.example.demo.facade;

import com.example.demo.domain.TypeForceUser;
import com.example.demo.facade.dto.TypeForceUserDTO;
import com.example.demo.facade.mapper.TypeForceUserMapper;
import com.example.demo.service.TypeForceUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class TypeForceUserFacade {

    private final Logger log = LoggerFactory.getLogger(TypeForceUserFacade.class);

    private final TypeForceUserMapper mapper;

    private final TypeForceUserService service;

    public TypeForceUserFacade(TypeForceUserMapper mapper, TypeForceUserService service) {
        this.mapper = mapper;
        this.service = service;
    }

    public TypeForceUserDTO save(TypeForceUserDTO typeForceUserDTO) {
        log.debug("Request to save TypeForceUser : {}", typeForceUserDTO);
        service.checkByForceUserId(typeForceUserDTO);
        TypeForceUser typeForceUser = mapper.toEntity(typeForceUserDTO);
        typeForceUser = service.save(typeForceUser);
        return mapper.toDto(typeForceUser);
    }

    public Optional<TypeForceUserDTO> partialUpdate(TypeForceUserDTO typeForceUserDTO) {
        log.debug("Request to partially update TypeForceUser : {}", typeForceUserDTO);
        return service
                .findOne(typeForceUserDTO.getId())
                .map(
                        existingTypeForceUser -> {
                            mapper.partialUpdate( existingTypeForceUser, typeForceUserDTO);
                            return  existingTypeForceUser;
                        }
                )
                .map(service::save)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<TypeForceUserDTO> findAll(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public TypeForceUserDTO findOne(Long id) {
        log.debug("Request to get TypeForceUser : {}", id);
        return mapper.toDto(service.findOne(id).orElseThrow());
    }

    public void delete(Long id) {
        log.debug("Request to delete TypeForceUser : {}", id);
        service.delete(id);
    }
}
