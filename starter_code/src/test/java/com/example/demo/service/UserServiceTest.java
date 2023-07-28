package com.example.demo.service;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetByUsername_ExistingUsername_ShouldReturnUser() {
        String existingUsername = "test";
        User expectedUser = User.builder().username(existingUsername).build();
        when(userRepository.findByUsername(existingUsername)).thenReturn(Optional.of(expectedUser));

        Assertions.assertTrue(userService.getByUsername(existingUsername).isPresent());
    }

    @Test
    public void testGetByUsername_NonExistingUsername_ShouldReturnEmptyOptional() {
        String nonExistingUsername = "test";
        when(userRepository.findByUsername(nonExistingUsername)).thenReturn(Optional.empty());

        Assertions.assertFalse(userService.getByUsername(nonExistingUsername).isPresent());
    }

}