package com.example.demo.service.impl;


import com.example.demo.domain.KnownMaster;
import com.example.demo.repository.KnownMastersRepository;
import com.example.demo.service.KnownMastersService;
import com.example.demo.service.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class KnownMastersServiceImpl implements KnownMastersService {

    private final KnownMastersRepository repository;

    public KnownMastersServiceImpl(KnownMastersRepository repository) {
        this.repository = repository;
    }

    // TODO: prevent the same master from being added to the same apprentice
    @Override
    public KnownMaster save(KnownMaster knownMaster) {

        return repository.save(knownMaster);
    }

    @Override
    public Page<KnownMaster> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<KnownMaster> findOne(@NonNull Long id) {
        return repository.findById(id);
    }

    @Override
    public List<KnownMaster> findAllByMasterId(Long id) {
        return repository.findAllByMasterId(id);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
