package com.healthy_plate.auth.infrastructure.oauth2;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo {

    private static final String PROVIDER = "naver";

    private final Map<String, Object> response;

    public NaverOAuth2UserInfo(final Map<String, Object> attributes) {
        Object responseObj = attributes.get("response");
        if (responseObj == null) {
            throw new IllegalArgumentException("Response object가 네이버 Oauth 응답에 존재하지 않습니다.");
        }
        this.response = extractMap(responseObj);
    }

    @Override
    public String getProvider() {
        return PROVIDER;
    }

    @Override
    public String getProviderId() {
        Object id = response.get("id");
        if (id == null) {
            throw new IllegalArgumentException("Provider ID가 네이버 Oauth 응답에 존재하지 않습니다.");
        }
        return id.toString();
    }

    @Override
    public String getEmail() {
        Object email = response.get("email");
        if (email == null) {
            throw new IllegalArgumentException("Email이 네이버 Oauth 응답에 존재하지 않습니다.");
        }
        return email.toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractMap(final Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        } else {
            throw new IllegalArgumentException("네이버 OAuth 응답 구조가 올바르지 않습니다.");
        }
    }
}
