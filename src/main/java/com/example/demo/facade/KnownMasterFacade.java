package com.example.demo.facade;

import com.example.demo.domain.KnownMaster;
import com.example.demo.facade.dto.KnownMasterDTO;
import com.example.demo.facade.mapper.KnownMasterMapper;
import com.example.demo.service.KnownMastersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class KnownMasterFacade {


    private final Logger log = LoggerFactory.getLogger(KnownMasterFacade.class);

    private final KnownMasterMapper mapper;

    private final KnownMastersService service;

    public KnownMasterFacade(KnownMasterMapper mapper, KnownMastersService service) {
        this.mapper = mapper;
        this.service = service;
    }


    public KnownMasterDTO save(KnownMasterDTO knownMasterDTO) {
        log.debug("Request to save KnownMaster : {}", knownMasterDTO);
        KnownMaster knownMaster = mapper.toEntity(knownMasterDTO);
        knownMaster = service.save(knownMaster);
        return mapper.toDto(knownMaster);
    }

    public Optional<KnownMasterDTO> partialUpdate(KnownMasterDTO knownMasterDTO) {
        log.debug("Request to partially update KnownMaster : {}", knownMasterDTO);
        return service
                .findOne(knownMasterDTO.getId())
                .map(
                        existingKnownMaster -> {
                            mapper.partialUpdate(existingKnownMaster, knownMasterDTO);
                            return existingKnownMaster;
                        }
                )
                .map(service::save)
                .map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public Page<KnownMasterDTO> findAll(Pageable pageable) {
        return service.findAll(pageable).map(mapper::toDto);
    }

    @Transactional(readOnly = true)
    public KnownMasterDTO findOne(Long id) {
        log.debug("Request to get KnownMaster : {}", id);
        return mapper.toDto(service.findOne(id).orElseThrow());
    }

    public void delete(Long id) {
        log.debug("Request to delete KnownMaster : {}", id);
        service.delete(id);
    }
}
