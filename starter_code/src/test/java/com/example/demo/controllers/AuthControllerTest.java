package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.LoginRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.TokenService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AuthControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<LoginRequest> json;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private AuthService authService;

    @Mock
    private User user;

    private LoginRequest request = LoginRequest.builder()
            .username("test")
            .password("test")
            .build();

    @Test
    void testLogin_WhenGiveCorrectAcc_ShouldReturnSuccess() throws Exception {
        given(tokenService.validateToken(any())).willReturn(Optional.of(user));
        given(authService.authenticate(any())).willReturn(Optional.of(user));

        mvc.perform(post(new URI("/api/auth/login"))
                                .content(json.write(request).getJson())
                                .contentType(MediaType.APPLICATION_JSON_UTF8)
                                .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    void testLogin_WhenGiveWrongAcc_ShouldReturnFailed() throws Exception {
        given(tokenService.validateToken(any())).willReturn(Optional.empty());
        given(authService.authenticate(any())).willReturn(Optional.empty());

        mvc.perform(post(new URI("/api/auth/login"))
                        .content(json.write(request).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized());
    }
}