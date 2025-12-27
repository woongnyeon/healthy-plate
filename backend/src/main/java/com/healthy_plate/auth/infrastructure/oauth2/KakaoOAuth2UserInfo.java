package com.healthy_plate.auth.infrastructure.oauth2;

import java.util.Map;

public class KakaoOAuth2UserInfo implements OAuth2UserInfo {

    private static final String PROVIDER = "kakao";

    private final Map<String, Object> attributes;
    private final Map<String, Object> kakaoAccount;

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
        Object kakaoAccountObj = attributes.get("kakao_account");
        if (kakaoAccountObj == null) {
            throw new IllegalArgumentException("Kakao account object가 카카오 Oauth 응답에 존재하지 않습니다.");
        }
        this.kakaoAccount = extractMap(kakaoAccountObj);
    }

    @Override
    public String getProvider() {
        return PROVIDER;
    }

    @Override
    public String getProviderId() {
        Object id = attributes.get("id");
        if (id == null) {
            throw new IllegalArgumentException("Provider ID가 카카오 Oauth 응답에 존재하지 않습니다.");
        }
        return id.toString();
    }

    @Override
    public String getEmail() {
        Object email = kakaoAccount.get("email");
        if (email == null) {
            throw new IllegalArgumentException("Email이 카카오 Oauth 응답에 존재하지 않습니다.");
        }
        return email.toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractMap(final Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        } else {
            throw new IllegalArgumentException("카카오 OAuth 응답 구조가 올바르지 않습니다.");
        }
    }
}
