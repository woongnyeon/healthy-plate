package com.healthy_plate.auth.infrastructure.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo implements OAuth2UserInfo {

    private static final String PROVIDER = "google";

    private final Map<String, Object> attributes;

    public GoogleOAuth2UserInfo(final Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return PROVIDER;
    }

    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }
}
