package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class OrderControllerTest {

    @Autowired
    private OrderController orderController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testSubmit_WhenGivenCorrectData_ShouldReturn200() {
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);
        cart.setTotal(BigDecimal.ONE);
        cart.setItems(new ArrayList<>());

        given(userRepository.findByUsername("")).willReturn(Optional.of(user));
        doReturn(null).when(userRepository).save(any());

        assertEquals(HttpStatus.OK, orderController.submit("").getStatusCode());
    }

    @Test
    void testSubmit_WhenGivenWrongData_ShouldReturn404() {
        User user = new User();
        Cart cart = new Cart();
        user.setCart(cart);
        cart.setUser(user);
        cart.setTotal(BigDecimal.ONE);
        cart.setItems(new ArrayList<>());

        given(userRepository.findByUsername("")).willReturn(Optional.of(user));
        doReturn(null).when(userRepository).save(any());

        assertEquals(HttpStatus.NOT_FOUND, orderController.submit("test").getStatusCode());
    }

    @Test
    void testGetOrdersForUser_WhenGivenCorrectData_ShouldReturn200() {
        given(userRepository.findByUsername("")).willReturn(Optional.of(new User()));
        given(orderRepository.findByUser(any())).willReturn(new ArrayList<>());

        assertEquals(HttpStatus.OK, orderController.getOrdersForUser("").getStatusCode());
    }

    @Test
    void testGetOrdersForUser_WhenGivenWrongData_ShouldReturn404() {
        given(userRepository.findByUsername("")).willReturn(Optional.of(new User()));
        given(orderRepository.findByUser(any())).willReturn(new ArrayList<>());

        assertEquals(HttpStatus.NOT_FOUND, orderController.getOrdersForUser("test").getStatusCode());
    }
}