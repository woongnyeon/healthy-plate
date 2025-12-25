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
            throw new IllegalArgumentException("Kakao account object is missing in Kakao OAuth2 attributes");
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
            throw new IllegalArgumentException("Provider ID is missing in Kakao OAuth2 response");
        }
        return id.toString();
    }

    @Override
    public String getEmail() {
        Object email = kakaoAccount.get("email");
        if (email == null) {
            throw new IllegalArgumentException("Email is missing in Kakao OAuth2 response");
        }
        return email.toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractMap(Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        } else {
            throw new IllegalArgumentException("Invalid kakao_account structure in Kakao OAuth2 response");
        }
    }
}
