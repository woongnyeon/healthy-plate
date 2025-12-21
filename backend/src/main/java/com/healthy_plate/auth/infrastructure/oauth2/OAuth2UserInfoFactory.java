package com.healthy_plate.auth.infrastructure.oauth2;

import com.healthy_plate.auth.domain.model.OAuth2Provider;
import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(OAuth2Provider provider, Map<String, Object> attributes) {
        return switch (provider) {
            case GOOGLE -> new GoogleOAuth2UserInfo(attributes);
            case KAKAO, NAVER -> throw new IllegalArgumentException("미설정 Oauth 입니다.");
        };
    }
}
