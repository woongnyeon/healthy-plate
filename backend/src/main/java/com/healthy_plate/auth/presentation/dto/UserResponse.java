package com.healthy_plate.auth.presentation.dto;

import com.healthy_plate.user.domain.model.User;

public record UserResponse(
    Long id,
    String email,
    String nickname,
    String profileImageUrl,
    String provider
) {

    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getEmail().getValue(),
            user.getProfile().getNickname(),
            user.getProfile().getProfileImageUrl(),
            user.getProvider().name()
        );
    }
}