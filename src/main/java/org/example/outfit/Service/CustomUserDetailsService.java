package org.example.outfit.Service;

import org.example.outfit.Repository.UserRepo;
import org.example.outfit.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepo userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with login: " + login));

        // Get the role as a string
        String roleString = user.getRole().toString();

        // Check if the role already has ROLE_ prefix
        String authority = roleString.startsWith("ROLE_") ? roleString : "ROLE_" + roleString;

        // Debug output to help diagnose issues
        System.out.println("Loading user: " + login + " with authority: " + authority);

        return org.springframework.security.core.userdetails.User
                .withUsername(login)
                .password(user.getPassword())
                .authorities(new SimpleGrantedAuthority(authority))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}