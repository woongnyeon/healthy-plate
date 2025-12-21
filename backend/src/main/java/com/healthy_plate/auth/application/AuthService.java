package com.healthy_plate.auth.application;

import com.healthy_plate.auth.domain.model.JwtTokenProvider;
import com.healthy_plate.auth.domain.model.RefreshToken;
import com.healthy_plate.auth.domain.repository.RefreshTokenRepository;
import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Transactional
    public String generateAccessToken(final String refreshTokenValue) {
        if (!jwtTokenProvider.validateToken(refreshTokenValue)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token 입니다.");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
            .orElseThrow(() -> new IllegalArgumentException("Refresh Token을 찾을 수 없습니다."));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(refreshTokenValue);
            throw new IllegalArgumentException("만료된 Refresh Token입니다.");
        }
        User user = userRepository.findById(refreshToken.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return jwtTokenProvider.generateAccessToken(
            user.getId(),
            user.getEmail().getValue(),
            user.getRole()
        );
    }

    @Transactional
    public User getUserFromRefreshToken(final String refreshTokenValue) {
        if (!jwtTokenProvider.validateToken(refreshTokenValue)) {
            throw new IllegalArgumentException("유효하지 않은 Refresh Token 입니다.");
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
            .orElseThrow(() -> new IllegalArgumentException("Refresh Token을 찾을 수 없습니다."));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(refreshTokenValue);
            throw new IllegalArgumentException("만료된 Refresh Token입니다.");
        }

        return userRepository.findById(refreshToken.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }

    @Transactional
    public String registerNickname(final String refreshTokenValue, final String nickname) {
        User user = getUserFromRefreshToken(refreshTokenValue);

        if (!user.isFirstLogin()) {
            throw new IllegalArgumentException("이미 닉네임이 등록된 사용자입니다.");
        }

        user.updateNickname(nickname);

        return jwtTokenProvider.generateAccessToken(
            user.getId(),
            user.getEmail().getValue(),
            user.getRole()
        );
    }

    @Transactional
    public void logout(String refreshTokenValue) {
        refreshTokenRepository.deleteByToken(refreshTokenValue);
    }
}
