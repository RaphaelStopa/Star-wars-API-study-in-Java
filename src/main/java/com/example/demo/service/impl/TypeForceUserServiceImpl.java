package com.example.demo.service.impl;

import com.example.demo.domain.TypeForceUser;
import com.example.demo.facade.dto.TypeForceUserDTO;
import com.example.demo.repository.TypeForceUserRepository;
import com.example.demo.service.TypeForceUserService;
import com.example.demo.service.exceptions.BadRequestAlertException;
import com.example.demo.service.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeForceUserServiceImpl implements TypeForceUserService {

    private static final String ENTITY_NAME = "TypeForceUser";

    private final TypeForceUserRepository repository;

    public TypeForceUserServiceImpl(TypeForceUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public TypeForceUser save(TypeForceUser typeForceUser) {
        return repository.save(typeForceUser);
    }


    @Override
    public Page<TypeForceUser> findAll(@NonNull Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<TypeForceUser> findOne(@NonNull Long id) {
        return repository.findById(id);
    }


    @Override
    public void checkByForceUserId(TypeForceUserDTO typeForceUserDTO) {

        if (Optional.ofNullable(typeForceUserDTO.getId()).isEmpty()) {
            throw new BadRequestAlertException("need a forceUser Id", ENTITY_NAME, "repeated record");
        }

        if (!repository.findByForceUserId(typeForceUserDTO.getId()).isEmpty()) {
            throw new BadRequestAlertException("already exists", ENTITY_NAME, "repeated record");

        }
    }

    @Override
    public List<TypeForceUser> findAllByForceUserId(Long id) {
        return repository.findAllByForceUserId(id);
    }


    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
