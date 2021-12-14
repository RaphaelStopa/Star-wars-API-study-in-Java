package com.example.demo.service;

import com.example.demo.domain.KnownMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface KnownMastersService {
    KnownMaster save(KnownMaster knownMaster);

    Optional<KnownMaster> partialUpdate(KnownMaster knownMaster);

    Page<KnownMaster> findAll(Pageable pageable);

    Optional<KnownMaster> findOne(Long id);

    List<KnownMaster> findAllByMasterId(Long id);

    List<KnownMaster> findAllByForceUserId(Long id);

    void delete(Long id);
}
