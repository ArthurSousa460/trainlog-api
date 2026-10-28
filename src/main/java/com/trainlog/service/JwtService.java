package com.trainlog.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String SECRET_KEY;
    private long expiration

    public JwtService(
            @Value("${autentication.jwt.secret}") String secretKey,
            @Value("${autentication.jwt.expiration}") long expiration
    ) {
        this.SECRET_KEY = secretKey;
        this.expiration = expiration;
    }



}

