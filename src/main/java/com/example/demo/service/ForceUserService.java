package com.example.demo.service;

import com.example.demo.domain.ForceUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ForceUserService {
    ForceUser save(ForceUser forceUser);

    Optional<ForceUser> partialUpdate(ForceUser forceUser);

    Page<ForceUser> findAll(Pageable pageable);

    Optional<ForceUser> findOne(Long id);

    void checkByName(String name);

    void delete(Long id);
}
