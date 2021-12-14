package com.example.demo.repository.querydsl;

import com.example.demo.domain.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserQueryRepository extends DslBaseRepository<User, Long> {

    Optional<User> findOneByEmailIgnoreCase(String email);
}
