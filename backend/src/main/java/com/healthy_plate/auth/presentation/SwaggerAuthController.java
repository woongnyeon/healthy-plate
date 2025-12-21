package com.healthy_plate.auth.presentation;

import com.healthy_plate.auth.presentation.dto.AuthResponse;
import com.healthy_plate.auth.presentation.dto.LoginSuccessResponse;
import com.healthy_plate.auth.presentation.dto.UpdateNicknameRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "인증", description = "인증 관련 API")
public interface SwaggerAuthController {

    @Operation(
            summary = "액세스 토큰 갱신",
            tags = "인증",
            description = "리프레시 토큰을 사용하여 새로운 액세스 토큰을 발급합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "토큰 갱신 성공",
                            content = @Content(
                                    schema = @Schema(implementation = LoginSuccessResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "유효하지 않은 리프레시 토큰",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    ResponseEntity<LoginSuccessResponse> refreshToken(HttpServletRequest request);

    @Operation(
            summary = "액세스 토큰 조회",
            tags = "인증",
            description = "리프레시 토큰을 사용하여 액세스 토큰과 사용자 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "조회 성공",
                            content = @Content(
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "유효하지 않은 리프레시 토큰",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    ResponseEntity<AuthResponse> getAccessToken(HttpServletRequest request);

    @Operation(
            summary = "닉네임 등록",
            tags = "인증",
            description = "사용자의 닉네임을 등록하고 업데이트된 액세스 토큰과 사용자 정보를 반환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "닉네임 등록 성공",
                            content = @Content(
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청 (유효성 검증 실패)",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "유효하지 않은 리프레시 토큰",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    ResponseEntity<AuthResponse> registerNickname(
            @Valid @RequestBody UpdateNicknameRequest request,
            HttpServletRequest httpRequest
    );

    @Operation(
            summary = "로그아웃",
            tags = "인증",
            description = "사용자를 로그아웃하고 리프레시 토큰을 무효화합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그아웃 성공",
                            content = @Content(
                                    schema = @Schema(implementation = Void.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "유효하지 않은 리프레시 토큰",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 요청",
                            content = @Content(
                                    schema = @Schema(implementation = String.class)
                            )
                    )
            }
    )
    ResponseEntity<Void> logout(
            @CookieValue(name = "refreshToken") String refreshToken,
            HttpServletResponse response
    );
}