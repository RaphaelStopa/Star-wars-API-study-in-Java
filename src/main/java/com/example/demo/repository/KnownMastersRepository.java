package com.example.demo.repository;

import com.example.demo.domain.KnownMaster;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnownMastersRepository extends JpaRepository<KnownMaster, Long> {
    List<KnownMaster> findAllByMasterId(Long id);
    List<KnownMaster> findAllByForceUserId(Long id);
}
