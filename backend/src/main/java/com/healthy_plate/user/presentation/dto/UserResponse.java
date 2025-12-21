package com.healthy_plate.user.presentation.dto;

import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.domain.model.UserProfile;

public record UserResponse(
    Long id,
    String email,
    String nickname,
    String profileImageUrl,
    String provider
) {

    public static UserResponse from(User user) {
        UserProfile profile = user.getProfile();
        return new UserResponse(
            user.getId(),
            user.getEmail().getValue(),
            profile != null ? profile.getNickname() : null,
            profile != null ? profile.getProfileImageUrl() : null,
            user.getProvider().name()
        );
    }
}