package com.healthy_plate.auth.presentation.dto;


public record LoginSuccessResponse(String accessToken) {

    public static LoginSuccessResponse from(String accessToken) {
        return new LoginSuccessResponse(accessToken);
    }
}
