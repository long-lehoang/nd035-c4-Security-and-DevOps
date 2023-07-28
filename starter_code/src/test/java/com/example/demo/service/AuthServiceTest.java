package com.example.demo.service;

import com.example.demo.model.domain.Credential;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.LoginRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testAuthenticate_GiveCorrectData_ShouldReturnValidUser() {
        Credential credential = Credential.builder().username("test").password("test").build();
        given(userRepository.findByUsername(any()))
                .willReturn(Optional.of(User.builder().username("test").password(passwordEncoder.encode("test")).build()));

        assertTrue(authService.authenticate(credential).isPresent());
    }

    @Test
    void testAuthenticate_GiveWrongData_ShouldReturnEmpty() {
        Credential credential = Credential.builder().username("test").password("123132").build();
        given(userRepository.findByUsername(any()))
                .willReturn(Optional.of(User.builder().username("test").password(passwordEncoder.encode("test")).build()));

        assertFalse(authService.authenticate(credential).isPresent());
    }
}