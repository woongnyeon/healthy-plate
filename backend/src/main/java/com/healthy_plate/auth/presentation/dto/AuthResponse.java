package com.healthy_plate.auth.presentation.dto;

import com.healthy_plate.user.domain.model.User;

public record AuthResponse(
    String accessToken,
    UserResponse user
) {

    public static AuthResponse of(String accessToken, User user) {
        return new AuthResponse(accessToken, UserResponse.from(user));
    }
}