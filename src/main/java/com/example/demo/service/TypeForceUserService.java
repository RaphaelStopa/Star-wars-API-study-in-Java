package com.example.demo.service;


import com.example.demo.domain.TypeForceUser;
import com.example.demo.facade.dto.TypeForceUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface TypeForceUserService {
    TypeForceUser save(TypeForceUser typeForceUser);

    Optional<TypeForceUser> partialUpdate(TypeForceUser typeForceUser);

    Page<TypeForceUser> findAll(Pageable pageable);

    Optional<TypeForceUser> findOne(Long id);

    List<TypeForceUser> findAllByForceUserId(Long id);

    void checkByForceUserId(TypeForceUserDTO typeForceUserDTO);

    void delete(Long id);
}
