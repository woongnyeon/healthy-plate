package com.healthy_plate.auth.domain.model;

public enum OAuth2Provider {
    
    GOOGLE("구글"),
    KAKAO("카카오"),
    NAVER("네이버");

    private final String displayName;

    OAuth2Provider(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
