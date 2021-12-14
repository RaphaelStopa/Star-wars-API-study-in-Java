package com.example.demo.repository;

import com.example.demo.domain.ForceUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ForceUserRepository extends JpaRepository<ForceUser, Long> {

    Optional<ForceUser> findByName(String name);
}
