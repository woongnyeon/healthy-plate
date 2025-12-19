package com.healthy_plate.auth.domain.service;

import com.healthy_plate.auth.domain.model.RefreshToken;
import com.healthy_plate.auth.domain.repository.RefreshTokenRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void saveRefreshToken(final Long userId, final String token, long refreshTokenExpiration) {
        refreshTokenRepository.findByUserId(userId)
            .ifPresent(existing -> refreshTokenRepository.deleteByToken(existing.getToken()));

        LocalDateTime expiryDate = LocalDateTime.now().plusSeconds(refreshTokenExpiration / 1000);
        RefreshToken refreshToken = new RefreshToken(token, userId, expiryDate);
        refreshTokenRepository.save(refreshToken);
    }
}
