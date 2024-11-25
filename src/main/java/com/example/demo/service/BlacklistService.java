package com.example.demo.service;


import com.example.demo.model.BlacklistedToken;
import com.example.demo.repository.BlacklistRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static javax.crypto.Cipher.SECRET_KEY;

@Service
public class BlacklistService {
    private final BlacklistRepository blacklistRepository;
    private static final String SECRET_KEY = "3Odff6jhez2aMSGysfX6s3B9u+VnEk3pPsmSAAIV3P6xwDLy9XK1p1pwzYWHEzdJ"; // Replace with your key

    @Autowired
    public BlacklistService(BlacklistRepository blacklistRepository) {
        this.blacklistRepository = blacklistRepository;
    }

    public void blacklistToken(String token) {

        // Extract expiration time from token
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseClaimsJws(token)
                .getBody();
        Date expirationDate = claims.getExpiration();

        // Save to database
        BlacklistedToken blacklistedToken = BlacklistedToken.builder()
                .token(token)
                .expirationDate(expirationDate)
                .build();

    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistRepository.existsByToken(token);
    }
}
