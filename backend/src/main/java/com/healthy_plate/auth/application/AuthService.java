package com.healthy_plate.auth.application;

import com.healthy_plate.auth.domain.model.JwtTokenProvider;
import com.healthy_plate.auth.domain.model.RefreshToken;
import com.healthy_plate.auth.domain.repository.RefreshTokenRepository;
import com.healthy_plate.auth.presentation.dto.UpdateNicknameRequest;
import com.healthy_plate.shared.error.exception.AuthenticationErrorCode;
import com.healthy_plate.shared.error.exception.BusinessErrorCode;
import com.healthy_plate.shared.error.exception.CustomAuthenticationException;
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
            throw new CustomAuthenticationException(AuthenticationErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
            .orElseThrow(() -> new CustomAuthenticationException(AuthenticationErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(refreshTokenValue);
            throw new CustomAuthenticationException(AuthenticationErrorCode.EXPIRED_REFRESH_TOKEN);
        }
        User user = userRepository.findById(refreshToken.getUserId())
            .orElseThrow(() -> new CustomAuthenticationException(BusinessErrorCode.USER_NOT_FOUND));

        // 프로필 미등록 사용자 차단
        if (user.isFirstLogin()) {
            throw new CustomAuthenticationException(AuthenticationErrorCode.PROFILE_REGISTRATION_REQUIRED);
        }

        return jwtTokenProvider.generateAccessToken(
            user.getId(),
            user.getEmail().getValue(),
            user.getRole()
        );
    }

    @Transactional
    public User getUserFromRefreshToken(final String refreshTokenValue) {
        if (!jwtTokenProvider.validateToken(refreshTokenValue)) {
            throw new CustomAuthenticationException(AuthenticationErrorCode.INVALID_REFRESH_TOKEN);
        }

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenValue)
            .orElseThrow(() -> new CustomAuthenticationException(AuthenticationErrorCode.REFRESH_TOKEN_NOT_FOUND));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.deleteByToken(refreshTokenValue);
            throw new CustomAuthenticationException(AuthenticationErrorCode.EXPIRED_REFRESH_TOKEN);
        }

        return userRepository.findById(refreshToken.getUserId())
            .orElseThrow(() -> new CustomAuthenticationException(BusinessErrorCode.USER_NOT_FOUND));
    }

    @Transactional
    public String registerNickname(final String refreshTokenValue, final UpdateNicknameRequest request) {
        User user = getUserFromRefreshToken(refreshTokenValue);
        user.updateProfile(request.nickname(),request.profileImageUrl(),request.introduction());
        userRepository.save(user);

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
