package com.healthy_plate.auth.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.healthy_plate.auth.domain.model.RefreshToken;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RefreshToken 테스트")
public class RefreshTokenTest {

    @Test
    @DisplayName("유효한 값으로 RefreshToken 객체를 생성한다")
    void createRefreshTokenWithValidValues() {
        String token = "test-refresh-token-12345";
        Long userId = 1L;
        LocalDateTime expiryDate = LocalDateTime.now().plusDays(7);

        // when
        RefreshToken refreshToken = new RefreshToken(token, userId, expiryDate);

        // then
        assertSoftly(softly -> {
            softly.assertThat(refreshToken.getToken()).isEqualTo(token);
            softly.assertThat(refreshToken.getUserId()).isEqualTo(userId);
            softly.assertThat(refreshToken.getExpiryDate()).isEqualTo(expiryDate);
        });
    }

    @Test
    @DisplayName("만료된 RefreshToken은 isExpired()가 true를 반환한다")
    void expiredRefreshTokenReturnsTrue() {
        // given
        String token = "expired-token";
        Long userId = 1L;
        LocalDateTime expiryDate = LocalDateTime.now().minusDays(1);

        // when
        RefreshToken refreshToken = new RefreshToken(token, userId, expiryDate);

        // then
        assertThat(refreshToken.isExpired()).isTrue();
    }
}
