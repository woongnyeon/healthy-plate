package com.healthy_plate.auth.infrastructure.oauth2;

import java.util.Map;

public class NaverOAuth2UserInfo implements OAuth2UserInfo{

    private static final String PROVIDER = "naver";

    private final Map<String, Object> response;

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        this.response = extractMap(attributes.get("response"));
    }

    @Override
    public String getProvider() {
        return PROVIDER;
    }

    @Override
    public String getProviderId() {
        return response.get("id").toString();
    }

    @Override
    public String getEmail() {
        return response.get("email").toString();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> extractMap(Object obj) {
        if (obj instanceof Map) {
            return (Map<String, Object>) obj;
        } else {
            throw new IllegalArgumentException("Invalid " + "response" + " structure in OAuth2 response");
        }
    }
}
