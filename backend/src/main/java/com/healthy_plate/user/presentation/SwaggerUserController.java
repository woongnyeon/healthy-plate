package com.healthy_plate.user.presentation;

import com.healthy_plate.shared.error.ErrorResponse;
import com.healthy_plate.user.presentation.dto.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "사용자", description = "사용자 관련 API")
public interface SwaggerUserController {

    @Operation(
        summary = "현재 로그인한 사용자 정보 조회",
        description = "액세스 토큰을 사용하여 현재 로그인한 사용자의 정보를 조회합니다.",
        security = @SecurityRequirement(name = "Bearer Authentication"),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "사용자 정보 조회 성공",
                content = @Content(
                    schema = @Schema(implementation = UserResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "인증 실패 (유효하지 않은 토큰)",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "404",
                description = "사용자를 찾을 수 없음",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal Long userId);

    @Operation(
        summary = "닉네임 중복 확인",
        description = "특정 닉네임이 이미 사용 중인지 확인합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "중복 확인 성공 (true: 중복됨, false: 사용 가능)",
                content = @Content(
                    schema = @Schema(implementation = Boolean.class)
                )
            )
        }
    )
    ResponseEntity<Boolean> isDuplicatedNickname(@PathVariable String nickname);
}
