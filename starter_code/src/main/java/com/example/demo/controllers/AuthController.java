package com.example.demo.controllers;

import com.example.demo.exception.UnauthorizedException;
import com.example.demo.model.domain.BaseResponse;
import com.example.demo.model.domain.Credential;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.LoginRequest;
import com.example.demo.service.AuthService;
import com.example.demo.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AuthController {
    private final TokenService tokenService;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Validated LoginRequest request){
        Optional<User> userOptional = authService.authenticate(Credential.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build());

        if (!userOptional.isPresent()){
            throw new UnauthorizedException(request.getUsername());
        }

        String token = tokenService.generateToken(userOptional.get());
        return ResponseEntity.ok(
                BaseResponse.builder().data(token).build()
        );
    }
}
