package com.example.demo.service;

import com.example.demo.model.persistence.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenServiceTest {
    @Autowired
    private TokenService tokenService;

    @Test
    void testJWTGenerateAndValidate_WhenGivenCorrectToken_ShouldReturnUser(){
        User user = User.builder()
                .username("test")
                .id(1L)
                .password("test")
                .build();
        String token = tokenService.generateToken(user);
        Optional<User> result = tokenService.validateToken(token);
        assertTrue(result.isPresent());
    }

    @Test
    void testJWTGenerateAndValidate_WhenGivenWrongToken_ShouldReturnEmpty(){
        User user = User.builder()
                .username("test")
                .id(1L)
                .password("test")
                .build();
        String token = tokenService.generateToken(user);
        Optional<User> result = tokenService.validateToken(token+"123123");
        assertFalse(result.isPresent());
    }
}