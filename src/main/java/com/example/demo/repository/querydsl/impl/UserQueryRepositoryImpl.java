package com.example.demo.repository.querydsl.impl;

import com.example.demo.domain.User;
import com.example.demo.repository.querydsl.UserQueryRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UserQueryRepositoryImpl extends DslBaseRepositoryImpl<User, Long> implements UserQueryRepository {

    public UserQueryRepositoryImpl(EntityManager em) {
        super(User.class, em);
    }

    //it's just an example
    @Override
    public Optional<User> findOneByEmailIgnoreCase(String email) {
        return Optional.ofNullable(jpaQueryFactory.select(user).from(user).where(user.email.endsWithIgnoreCase(email)).fetchFirst());
    }
}
