package com.example.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.model.persistence.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.example.demo.util.Constants.USERNAME_CLAIM;
import static com.example.demo.util.Constants.USER_ID_CLAIM;

@Service
@Slf4j
public class TokenService {
    @Value("${app.security.jwt.secret-key}")
    private String secretKey;
    @Value("${app.security.jwt.issuer}")
    private String issuer;
    @Value("${app.security.jwt.expire-in-seconds}")
    private Long expireInTimes;

    public String generateToken(User user) {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Date currDate = new Date();
        Date expireDate = new Date(currDate.getTime() + expireInTimes * 1000);
        return JWT.create()
                .withIssuer(issuer)
                .withClaim(USER_ID_CLAIM, user.getId())
                .withClaim(USERNAME_CLAIM, user.getUsername())
                .withIssuedAt(currDate)
                .withExpiresAt(expireDate)
                .sign(algorithm);
    }

    public Optional<User> validateToken(String token) {
        final Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issuer)
                .build();

        try {
            DecodedJWT decodedJWT = verifier.verify(token);
            User user = User.builder()
                    .id(decodedJWT.getClaim(USER_ID_CLAIM).asLong())
                    .username(USERNAME_CLAIM)
                    .build();
            return Optional.of(user);
        } catch (JWTVerificationException e) {
            log.info("Verify Token Failed");
            return Optional.empty();
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.empty();
        }
    }
}
