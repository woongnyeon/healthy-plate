package com.healthy_plate.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

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
        Email email = Email.of("test@example.com");
        UserProfile profile = UserProfile.of("테스트유저", "https://example.com/profile.jpg", "안녕하세요 꿈나무입니다.");
        OAuth2Provider provider = OAuth2Provider.GOOGLE;
        String providerId = "google-123456";
        UserRole role = UserRole.ROLE_USER;

        // when
        User user = new User(email, profile, provider, providerId, role);

        // then
        assertThat(user.getEmail()).isEqualTo(email);
        assertThat(user.getProfile()).isEqualTo(profile);
        assertThat(user.getProvider()).isEqualTo(provider);
        assertThat(user.getProviderId()).isEqualTo(providerId);
        assertThat(user.getRole()).isEqualTo(role);
    }

    @Test
    @DisplayName("Google 제공자로 User 객체를 생성한다")
    void createUserWithGoogleProvider() {
        // given
        Email email = Email.of("google@example.com");
        UserProfile profile = UserProfile.of("구글유저", null, "안녕하세요 꿈나무입니다.");
        OAuth2Provider provider = OAuth2Provider.GOOGLE;
        String providerId = "google-123";

        // when
        User user = new User(email, profile, provider, providerId, UserRole.ROLE_USER);

        // then
        assertThat(user.getProvider()).isEqualTo(OAuth2Provider.GOOGLE);
        assertThat(user.getProviderId()).isEqualTo("google-123");
    }

    @Test
    @DisplayName("Kakao 제공자로 User 객체를 생성한다")
    void createUserWithKakaoProvider() {
        // given
        Email email = Email.of("kakao@example.com");
        UserProfile profile = UserProfile.of("카카오유저", null, "안녕하세요 꿈나무입니다.");
        OAuth2Provider provider = OAuth2Provider.KAKAO;
        String providerId = "kakao-456";

        // when
        User user = new User(email, profile, provider, providerId, UserRole.ROLE_USER);

        // then
        assertThat(user.getProvider()).isEqualTo(OAuth2Provider.KAKAO);
        assertThat(user.getProviderId()).isEqualTo("kakao-456");
    }

    @Test
    @DisplayName("Naver 제공자로 User 객체를 생성한다")
    void createUserWithNaverProvider() {
        // given
        Email email = Email.of("naver@example.com");
        UserProfile profile = UserProfile.of("네이버유저", null, "안녕하세요 꿈나무입니다.");
        OAuth2Provider provider = OAuth2Provider.NAVER;
        String providerId = "naver-789";

        // when
        User user = new User(email, profile, provider, providerId, UserRole.ROLE_USER);

        // then
        assertThat(user.getProvider()).isEqualTo(OAuth2Provider.NAVER);
        assertThat(user.getProviderId()).isEqualTo("naver-789");
    }

    @Test
    @DisplayName("ADMIN 권한으로 User 객체를 생성한다")
    void createUserWithAdminRole() {
        // given
        Email email = Email.of("admin@example.com");
        UserProfile profile = UserProfile.of("관리자", null, "안녕하세요 꿈나무입니다.");

        // when
        User user = new User(email, profile, OAuth2Provider.GOOGLE, "admin-123", UserRole.ROLE_ADMIN);

        // then
        assertThat(user.getRole()).isEqualTo(UserRole.ROLE_ADMIN);
    }
}