package com.manhattan.demo.Infrastructure.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.manhattan.demo.Entities.User.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public String generateToken(UserEntity userEntity){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer("manhattan-auth")//define em que sistema o token foi feito
                    .withSubject(userEntity.getId()) // identifica para qual usuario o token vai
                    .withExpiresAt(this.generateExpirationDate()) // puxa quanto tempo o token é valido
                    .sign(algorithm);

            return token;

        } catch (JWTCreationException exception){
            throw new RuntimeException("Error creating token");
        }
    }

    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("manhattan-auth")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception){
            return null;
        }
    }

    private Instant generateExpirationDate() {
        return OffsetDateTime
                .now(ZoneOffset.of("-03:00")) // já pega hora em -3
                .plusSeconds(30)
                .toInstant();
    }

}