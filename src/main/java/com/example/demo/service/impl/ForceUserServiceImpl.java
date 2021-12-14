package com.example.demo.service.impl;

import com.example.demo.domain.ForceUser;
import com.example.demo.repository.ForceUserRepository;
import com.example.demo.service.ForceUserService;
import com.example.demo.service.exceptions.BadRequestAlertException;
import com.example.demo.service.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForceUserServiceImpl implements ForceUserService {

    private static final String ENTITY_NAME = "forceUser";

    private final ForceUserRepository repository;

    private final TypeForceUserServiceImpl typeForceUserRepository;

    private final KnownMastersServiceImpl knownMastersService;

    public ForceUserServiceImpl(ForceUserRepository repository, TypeForceUserServiceImpl typeForceUserRepository, KnownMastersServiceImpl knownMastersService) {
        this.repository = repository;
        this.typeForceUserRepository = typeForceUserRepository;
        this.knownMastersService = knownMastersService;
    }

    @Override
    public ForceUser save(ForceUser forceUser) {
        return repository.save(forceUser);
    }

    @Override
    public Optional<ForceUser> partialUpdate(ForceUser forceUser) {
        if (!repository.existsById(forceUser.getId())) {
            throw BusinessException.badRequest();
        }
        return Optional.of(repository.save(forceUser));
    }


    @Override
    public Page<ForceUser> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<ForceUser> findOne(@NonNull Long id) {
        return repository.findById(id);
    }


    @Override
    public void checkByName(String name) {

        if (Optional.ofNullable(name).isEmpty()) {
            throw new BadRequestAlertException("need a name", ENTITY_NAME, "nonexistent name");

        }

        if (name.isBlank()) {
            throw new BadRequestAlertException("need a name", ENTITY_NAME, "nonexistent name");

        }

        if (!repository.findByName(name).isEmpty()) {
            throw new BadRequestAlertException("already exists with this name", ENTITY_NAME, "repeated name");

        }
    }

    @Override
    public void delete(Long id) {
        typeForceUserRepository.findAllByForceUserId(id).forEach(it-> typeForceUserRepository.delete(it.getId()));
        knownMastersService.findAllByMasterId(id).forEach(it-> knownMastersService.delete(it.getId()));
        knownMastersService.findAllByMasterId(id).forEach(it-> knownMastersService.delete(it.getId()));
        repository.deleteById(id);
    }
}
