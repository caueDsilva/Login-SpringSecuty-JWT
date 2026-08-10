package com.example.infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.example.domain.user.User;

@Service
public class TokenService {
    
    @Value("${api.security.token.secret}")
    private String secret;
    public String generateToken(User user) {
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create().
            withIssuer("login-auth-api").
            withSubject(user.getId()).
            withExpiresAt(generateExpirationDate()).
            sign(algorithm);

            return token;

            //withIssuer("login-auth-api") -> quem está emitindo o token, pode ser algum microserviço, ou a própria aplicação.
            //withSubject(user.getId()) -> quem é o dono do token, nesse caso o id do usuário.
            //sign(algorithm) -> assina o token com o algoritmo e a chave secreta.
        } catch (JWTCreationException e) {
            throw new RuntimeException("Error generating token", e);
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")).truncatedTo(ChronoUnit.SECONDS);
    }


}
