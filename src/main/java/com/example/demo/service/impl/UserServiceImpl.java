package com.example.demo.service.impl;

import com.example.demo.domain.Authority;
import com.example.demo.domain.User;
import com.example.demo.facade.dto.AdminUserDTO;
import com.example.demo.repository.AuthorityRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.exceptions.*;
import com.example.demo.security.AuthoritiesConstants;
import com.example.demo.security.SecurityUtils;
import com.example.demo.service.UserService;

import com.example.demo.util.RandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String ENTITY_NAME = "user";

    private final UserRepository userRepository;

    private final AuthorityRepository authorityRepository;

    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(AdminUserDTO userDTO, String password) {


        if (Optional.ofNullable(userDTO.getLogin()).isEmpty()) {
            throw new BadRequestAlertException("need a login", ENTITY_NAME, "nonexistent login");
        }

        if (userDTO.getLogin().isBlank()) {
            throw new BadRequestAlertException("can not be blank", ENTITY_NAME, "nonexistent login");
        }

        if (Optional.ofNullable(userDTO.getEmail()).isEmpty()) {
            throw new BadRequestAlertException("need a email", ENTITY_NAME, "nonexistent email");
        }

        if (userDTO.getEmail().isBlank()) {
            throw new BadRequestAlertException("can not be blank", ENTITY_NAME, "nonexistent email");
        }

        //I put a simple email validation, for the front would already be able to validate the email and
        // even if I don't, the account was only to appear as active after the account confirmation

        userRepository
                .findOneByLogin(userDTO.getLogin().toLowerCase())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new UsernameAlreadyUsedException();
                    }
                });
        userRepository
                .findOneByEmailIgnoreCase(userDTO.getEmail())
                .ifPresent(existingUser -> {
                    boolean removed = removeNonActivatedUser(existingUser);
                    if (!removed) {
                        throw new EmailAlreadyUsedException();
                    }
                });
        User newUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(userDTO.getLogin().toLowerCase());
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(userDTO.getFirstName());
        newUser.setLastName(userDTO.getLastName());
        if (userDTO.getEmail() != null) {
            newUser.setEmail(userDTO.getEmail().toLowerCase());
        }
        newUser.setImageUrl(userDTO.getImageUrl());
        newUser.setLangKey(userDTO.getLangKey());
        // A new user would be not active, BUT this is a study.
        newUser.setActivated(true);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        Set<Authority> authorities = new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        newUser.setAuthorities(authorities);
        newUser.setId(null);
        //I will not implement it because it is a study.
        newUser.setCreatedBy("system");
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }


    private boolean removeNonActivatedUser(User existingUser) {
        if (existingUser.isActivated()) {
            return false;
        }
        userRepository.delete(existingUser);
        userRepository.flush();
        return true;
    }

    @Transactional
    public void changePassword(String currentClearTextPassword, String newPassword) {
        SecurityUtils
                .getCurrentUserLogin()
                .flatMap(userRepository::findOneByLogin)
                .ifPresent(user -> {
                    String currentEncryptedPassword = user.getPassword();
                    if (!passwordEncoder.matches(currentClearTextPassword, currentEncryptedPassword)) {
                        throw new InvalidPasswordException();
                    }
                    String encryptedPassword = passwordEncoder.encode(newPassword);
                    user.setPassword(encryptedPassword);
                    log.debug("Changed password for User: {}", user);
                });
    }
}
