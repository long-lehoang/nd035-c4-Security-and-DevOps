package com.example.demo.config;

import com.example.demo.model.persistence.User;
import com.example.demo.service.TokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.example.demo.util.Constants.PREFIX_AUTH_HEADER;

@Component
@AllArgsConstructor
public class JWTFilterConfiguration extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith(PREFIX_AUTH_HEADER)){
            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }

        String token = authHeader.split(PREFIX_AUTH_HEADER)[1];
        Optional<User> userOptional = tokenService.validateToken(token);
        if (!userOptional.isPresent()){
            doFilter(httpServletRequest, httpServletResponse, filterChain);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userOptional.get(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        doFilter(httpServletRequest, httpServletResponse, filterChain);
    }
}
