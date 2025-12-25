package com.healthy_plate.auth.infrastructure.oauth2;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo{

    private static final String PROVIDER = "naver";

    private final Map<String, Object> response;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        Object responseObj = attributes.get("response");
        if (responseObj == null) {
            throw new IllegalArgumentException("Response object is missing in Naver OAuth2 attributes");
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
            throw new IllegalArgumentException("Provider ID is missing in Naver OAuth2 response");
        }
        return id.toString();
    }

    @Override
    public String getEmail() {
        Object email = response.get("email");
        if (email == null) {
            throw new IllegalArgumentException("Email is missing in Naver OAuth2 response");
        }
        return email.toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractMap(Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        } else {
            throw new IllegalArgumentException("Invalid response structure in Naver OAuth2 response");
        }
    }
}
