package com.healthy_plate.auth.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateNicknameRequest(
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 50, message = "닉네임은 2-50자 사이여야 합니다.")
    String nickname,
    String profileImageUrl,
    @Size(max = 500, message = "자기소개는 최대 500자까지 입력할 수 있습니다.")
    String introduction
) {

}