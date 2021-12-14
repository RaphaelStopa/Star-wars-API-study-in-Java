package com.example.demo.repository;

import com.example.demo.domain.TypeForceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TypeForceUserRepository extends JpaRepository<TypeForceUser, Long> {

    Optional<TypeForceUser> findByForceUserId(Long id);

    List<TypeForceUser> findAllByForceUserId(Long id);
}
