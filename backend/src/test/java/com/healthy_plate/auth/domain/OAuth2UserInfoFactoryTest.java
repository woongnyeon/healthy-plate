package com.healthy_plate.auth.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.healthy_plate.auth.domain.model.OAuth2Provider;
import com.healthy_plate.auth.infrastructure.oauth2.GoogleOAuth2UserInfo;
import com.healthy_plate.auth.infrastructure.oauth2.KakaoOAuth2UserInfo;
import com.healthy_plate.auth.infrastructure.oauth2.NaverOAuth2UserInfo;
import com.healthy_plate.auth.infrastructure.oauth2.OAuth2UserInfo;
import com.healthy_plate.auth.infrastructure.oauth2.OAuth2UserInfoFactory;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("OAuth2UserInfoFactory 테스트")
public class OAuth2UserInfoFactoryTest {

    @Test
    @DisplayName("Google Provider로 GoogleOAuth2UserInfo 객체를 생성한다")
    void createGoogleOAuth2UserInfo() {
        // given
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("sub", "google-123");
        attributes.put("email", "test@gmail.com");

        // when
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(OAuth2Provider.GOOGLE, attributes);

        // then
        assertThat(userInfo).isInstanceOf(GoogleOAuth2UserInfo.class);
    }

    @Test
    @DisplayName("Kakao Provider로 KakaoOAuth2UserInfo 객체를 생성한다")
    void createKakaoOAuth2UserInfo() {
        // given
        Map<String, Object> kakaoAccount = new HashMap<>();
        kakaoAccount.put("email", "test@kakao.com");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("id", 123456789L);
        attributes.put("kakao_account", kakaoAccount);

        // when
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(OAuth2Provider.KAKAO, attributes);

        // then
        assertThat(userInfo).isInstanceOf(KakaoOAuth2UserInfo.class);
    }

    @Test
    @DisplayName("Naver Provider로 NaverOAuth2UserInfo 객체를 생성한다")
    void createNaverOAuth2UserInfo() {
        // given
        Map<String, Object> response = new HashMap<>();
        response.put("id", "naver-123");
        response.put("email", "test@naver.com");

        Map<String, Object> attributes = new HashMap<>();
        attributes.put("response", response);

        // when
        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(OAuth2Provider.NAVER, attributes);

        // then
        assertThat(userInfo).isInstanceOf(NaverOAuth2UserInfo.class);
    }


}
