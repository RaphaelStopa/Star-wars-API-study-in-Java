package com.example.demo.service.impl;

import com.example.demo.domain.Authority;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.demo.domain.User usuario = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + username));

        return User
                .builder()
                .username(usuario.getEmail())
                .password(usuario.getPassword())
                .authorities(mapToGrantedAuthorities(usuario.getAuthorities()))
                .build();
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(Set<Authority> perfilEnum) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Authority authority: perfilEnum) {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        }

        return authorities;
    }

}