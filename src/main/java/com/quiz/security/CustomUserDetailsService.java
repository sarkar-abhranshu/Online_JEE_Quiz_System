package com.quiz.security;

import com.quiz.model.User;
import com.quiz.repository.UserRepository;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/*
    SOLID/GRASP highlights:
    - SRP (Single Responsibility): Focuses on mapping application User data to Spring Security user details.
    - DIP (Dependency Inversion): Depends on UserRepository abstraction injected via constructor.
    - GRASP Indirection: Acts as an adapter/indirection layer between domain user model and framework authentication contract.
*/
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
        throws UsernameNotFoundException {
        User user = userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException(
                    "User not found with email: " + email
                )
            );

        String authority = user.getRole();
        if (authority == null || authority.trim().isEmpty()) {
            authority = "STUDENT";
        }
        // Backward compatibility: some rows were stored with role/discriminator "User".
        if (authority.equalsIgnoreCase("user")) {
            authority = "STUDENT";
        }

        return new org.springframework.security.core.userdetails.User(
            user.getEmail(),
            user.getPassword(),
            Collections.singletonList(new SimpleGrantedAuthority(authority))
        );
    }

    public User getUserByEmail(String email) {
        return userRepository
            .findByEmail(email)
            .orElseThrow(() ->
                new UsernameNotFoundException(
                    "User not found with email: " + email
                )
            );
    }
}
