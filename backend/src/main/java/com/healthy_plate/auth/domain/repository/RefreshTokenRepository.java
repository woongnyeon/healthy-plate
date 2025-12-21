package com.healthy_plate.auth.domain.repository;

import com.healthy_plate.auth.domain.model.RefreshToken;
import java.util.Optional;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    void deleteByToken(String token);
}
