package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.LoginRequest;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.TokenService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CartControllerTest {
    @Autowired
    private CartController cartController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private CartRepository cartRepository;

    private final LoginRequest loginRequest = LoginRequest.builder()
            .username("test")
            .password("test")
            .build();

    private final ModifyCartRequest request = ModifyCartRequest.builder()
            .itemId(1L)
            .quantity(1)
            .build();
    @Mock
    private User user;
    @Mock
    private Item item;

    private final Cart cart = new Cart();

    @BeforeEach
    void setup() throws Exception {
        given(userRepository.findByUsername("test")).willReturn(Optional.of(user));
        given(tokenService.validateToken("test")).willReturn(Optional.of(user));
        given(itemRepository.findById(any())).willReturn(Optional.of(item));
        doReturn(new BigDecimal(1)).when(item).getPrice();
        doReturn(cart).when(cartRepository).save(any());
        doReturn(cart).when(user).getCart();
    }

    @Test
    void testAddToCart_WhenGivenCorrectData_ShouldReturnSuccess() {
        request.setUsername("test");
        assertEquals(HttpStatus.OK, cartController.addTocart(request).getStatusCode());
    }

    @Test
    void testAddToCart_WhenGivenWrongData_ShouldReturn404() {
        request.setUsername("1123");
        assertEquals(HttpStatus.NOT_FOUND, cartController.addTocart(request).getStatusCode());
    }

    @Test
    void testRemoveFromCart_WhenGivenCorrectData_ShouldReturnSuccess() {
        request.setUsername("test");
        assertEquals(HttpStatus.OK, cartController.removeFromcart(request).getStatusCode());
    }

    @Test
    void testRemoveFromCart_WhenGivenWrongData_ShouldReturn404() {
        request.setUsername("1123");
        assertEquals(HttpStatus.NOT_FOUND, cartController.removeFromcart(request).getStatusCode());
    }
}