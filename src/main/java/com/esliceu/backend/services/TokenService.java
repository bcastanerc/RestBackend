package com.esliceu.backend.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TokenService {
    @Value("${token.secret}")
    String tokenSecret;

    @Value("${token.expiration.time}")
    int tokenExpirationTime;

    Gson gson = new Gson();

    public String newToken(String user) {
        String token = JWT.create()
                .withSubject(user)
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .sign(Algorithm.HMAC512(tokenSecret.getBytes()));
        return token;
    }

    public String getSubject(String token) {
        String subject = JWT.require(Algorithm.HMAC512(tokenSecret.getBytes()))
                .build()
                .verify(token)
                .getSubject();
        return subject;
    }
}