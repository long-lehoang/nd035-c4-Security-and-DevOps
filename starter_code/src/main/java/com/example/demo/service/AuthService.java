package com.example.demo.service;

import com.example.demo.model.domain.Credential;
import com.example.demo.model.persistence.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public Optional<User> authenticate(Credential credential) {
        Optional<User> userOptional = userService.getByUsername(credential.getUsername());
        if (!userOptional.isPresent()) {
            return Optional.empty();
        }

        if (passwordEncoder.matches(credential.getPassword(), userOptional.get().getPassword())) {
            return userOptional;
        }

        return Optional.empty();
    }
}
