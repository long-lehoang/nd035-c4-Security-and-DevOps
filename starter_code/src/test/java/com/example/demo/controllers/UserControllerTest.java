package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CartRepository cartRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testFindById_WhenNormalCase_ShouldReturn200() {
        given(userRepository.findById(1L)).willReturn(Optional.of(new User()));

        assertEquals(HttpStatus.OK, userController.findById(1L).getStatusCode());
    }

    @Test
    void testFindById_WhenGivenNonExistedId_ShouldReturn404() {
        given(userRepository.findById(1L)).willReturn(Optional.of(new User()));

        assertEquals(HttpStatus.NOT_FOUND, userController.findById(2L).getStatusCode());
    }

    @Test
    void testFindByUserName_WhenNormalCase_ShouldReturn200() {
        given(userRepository.findByUsername("")).willReturn(Optional.of(new User()));

        assertEquals(HttpStatus.OK, userController.findByUserName("").getStatusCode());
    }

    @Test
    void testFindByUserName_WhenGivenNonExistedUsername_ShouldReturn404() {
        given(userRepository.findByUsername("test")).willReturn(Optional.of(new User()));

        assertEquals(HttpStatus.NOT_FOUND, userController.findByUserName("").getStatusCode());
    }

    @Test
    void testCreateUser_WhenNormalCase_ShouldReturn200() {
        CreateUserRequest request = CreateUserRequest.builder()
                .username("test")
                .password("test")
                .build();

        given(userRepository.save(any())).willReturn(new User());
        given(cartRepository.save(any())).willReturn(new Cart());

        assertEquals(HttpStatus.OK, userController.createUser(request).getStatusCode());
    }
}