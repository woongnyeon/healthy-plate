package com.healthy_plate.auth.domain.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public record JwtProperties(
    @Value("${jwt.secret-key}") String secretKey,
    @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
    @Value("${jwt.refresh-token-expiration}") long refreshTokenExpiration
) {

}
