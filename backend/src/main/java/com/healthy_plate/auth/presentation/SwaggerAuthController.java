package com.healthy_plate.auth.presentation;

import com.healthy_plate.auth.presentation.dto.TokenResponse;
import com.healthy_plate.auth.presentation.dto.RegisterUserProfileRequest;
import com.healthy_plate.shared.error.ErrorResponse;
import com.healthy_plate.shared.s3.PresignedUrlRequest;
import com.healthy_plate.shared.s3.PresignedUrlResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;

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
                    schema = @Schema(implementation = TokenResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "유효하지 않은 리프레시 토큰",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<TokenResponse> getAccessToken(HttpServletRequest request);

    @Operation(
        summary = "프로필 이미지 업로드 URL 생성 (회원가입용)",
        tags = "인증",
        description = """
            회원가입 시 프로필 이미지를 업로드하기 위한 Presigned URL을 생성합니다.

            **요청 형식:**
            - Content-Type: application/json
            - Body: { "contentType": "image/jpeg", "fileSize": 2097152 }

            **지원하는 이미지 형식:**
            - JPEG (image/jpeg)
            - PNG (image/png)
            - WEBP (image/webp)

            **파일 크기 제한:**
            - 최대 5MB (5,242,880 bytes)
            - 프론트엔드와 백엔드 모두에서 검증

            **사용 순서:**
            1. 프론트엔드에서 파일 선택 시 file.type과 file.size 가져오기
            2. 프론트엔드에서 파일 크기 체크 (5MB 이하인지 확인)
            3. 이 API를 JSON으로 호출하여 presignedUrl과 fileUrl을 받습니다
            4. presignedUrl로 이미지를 S3에 직접 PUT 업로드합니다
            5. PATCH /api/auth/register 호출 시 fileUrl을 profileImageUrl에 포함합니다

            **인증:** refresh_token 쿠키 사용 (회원가입 전이므로 accessToken 없음)
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Presigned URL 생성 성공",
                content = @Content(
                    schema = @Schema(implementation = PresignedUrlResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (지원하지 않는 파일 형식, 파일 크기 초과, contentType/fileSize 누락 등)",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "유효하지 않은 리프레시 토큰",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<PresignedUrlResponse> getPresignedUrl(
        PresignedUrlRequest request,
        HttpServletRequest httpRequest
    );

    @Operation(
        summary = "프로필 등록 (회원가입 완료)",
        tags = "인증",
        description = """
            OAuth2 로그인 후 사용자의 프로필을 등록하고 액세스 토큰을 반환합니다.

            **이미지 업로드 방법:**
            1. POST /api/auth/profile-image/presigned-url로 업로드 URL 받기
            2. 받은 presignedUrl로 이미지를 S3에 직접 PUT 업로드
            3. 이 API 호출 시 fileUrl을 profileImageUrl에 포함

            **필수:** nickname (2-50자)
            **선택:** profileImageUrl (S3 URL), introduction (최대 500자)

            **인증:** refresh_token 쿠키 사용
            """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "프로필 등록 성공",
                content = @Content(
                    schema = @Schema(implementation = TokenResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (유효성 검증 실패, 파일 형식 오류, 파일 크기 초과 등)",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "401",
                description = "유효하지 않은 리프레시 토큰",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<TokenResponse> registerUserInfo(
        RegisterUserProfileRequest request,
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
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<Void> logout(
        @CookieValue(name = "refresh_token") String refreshToken,
        HttpServletResponse response
    );
}