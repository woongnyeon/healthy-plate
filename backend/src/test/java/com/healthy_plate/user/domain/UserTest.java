package com.healthy_plate.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.healthy_plate.auth.domain.model.OAuth2Provider;
import com.healthy_plate.user.domain.model.Email;
import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.domain.model.UserProfile;
import com.healthy_plate.user.domain.model.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User 도메인 테스트")
class UserTest {

    @Test
    @DisplayName("유효한 값으로 User 객체를 생성한다")
    void createUserWithValidValues() {
        // given
        Email email = Email.from("test@example.com");
        UserProfile profile = UserProfile.of("테스트유저", "https://example.com/profile.jpg", "안녕하세요 꿈나무입니다.");
        OAuth2Provider provider = OAuth2Provider.GOOGLE;
        String providerId = "google-123456";

        // when
        User user = User.createOAuth2UserWithProfile(email, profile, provider, providerId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(user.getEmail()).isEqualTo(email);
            softly.assertThat(user.getProfile()).isEqualTo(profile);
            softly.assertThat(user.getProvider()).isEqualTo(provider);
            softly.assertThat(user.getProviderId()).isEqualTo(providerId);
            softly.assertThat(user.getRole()).isEqualTo(UserRole.ROLE_USER);
        });
    }

    @Test
    @DisplayName("Google 제공자로 User 객체를 생성한다")
    void createUserWithGoogleProvider() {
        // given
        Email email = Email.from("google@example.com");
        OAuth2Provider provider = OAuth2Provider.GOOGLE;
        String providerId = "google-123";

        // when
        User user = User.createOAuth2User(email, provider, providerId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(user.getProvider()).isEqualTo(OAuth2Provider.GOOGLE);
            softly.assertThat(user.getProviderId()).isEqualTo("google-123");
        });
    }

    @Test
    @DisplayName("Kakao 제공자로 User 객체를 생성한다")
    void createUserWithKakaoProvider() {
        // given
        Email email = Email.from("kakao@example.com");
        OAuth2Provider provider = OAuth2Provider.KAKAO;
        String providerId = "kakao-456";

        // when
        User user = User.createOAuth2User(email, provider, providerId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(user.getProvider()).isEqualTo(OAuth2Provider.KAKAO);
            softly.assertThat(user.getProviderId()).isEqualTo("kakao-456");
        });
    }

    @Test
    @DisplayName("Naver 제공자로 User 객체를 생성한다")
    void createUserWithNaverProvider() {
        // given
        Email email = Email.from("naver@example.com");
        OAuth2Provider provider = OAuth2Provider.NAVER;
        String providerId = "naver-789";

        // when
        User user = User.createOAuth2User(email, provider, providerId);

        // then
        assertSoftly(softly -> {
            softly.assertThat(user.getProvider()).isEqualTo(OAuth2Provider.NAVER);
            softly.assertThat(user.getProviderId()).isEqualTo("naver-789");
        });
    }

    @Test
    @DisplayName("ADMIN 권한으로 User 객체를 생성한다")
    void createUserWithAdminRole() {
        // given
        Email email = Email.from("admin@example.com");
        UserProfile profile = UserProfile.of("관리자", null, "안녕하세요 꿈나무입니다.");

        // when
        User user = User.createAdminUser(email, profile, OAuth2Provider.GOOGLE, "admin-123");

        // then
        assertThat(user.getRole()).isEqualTo(UserRole.ROLE_ADMIN);
    }
}