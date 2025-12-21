package com.healthy_plate.auth.infrastructure.oauth2;

public interface OAuth2UserInfo {

    String getProvider();

    String getProviderId();

    String getEmail();

    String getName();
}
