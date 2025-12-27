package com.healthy_plate.user.presentation;

import com.healthy_plate.shared.error.ErrorResponse;
import com.healthy_plate.shared.s3.PresignedUrlRequest;
import com.healthy_plate.shared.s3.PresignedUrlResponse;
import com.healthy_plate.user.presentation.dto.UpdateProfileRequest;
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

    @Operation(
        summary = "프로필 이미지 업로드 URL 생성 (프로필 수정용)",
        description = """
            프로필 수정 시 이미지를 업로드하기 위한 Presigned URL을 생성합니다.

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
            5. PATCH /api/users/profile 호출 시 fileUrl을 profileImageUrl에 포함합니다

            **인증:** Bearer accessToken 필요 (로그인 후)
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
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
                description = "인증 실패 (유효하지 않은 토큰)",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
                )
            )
        }
    )
    ResponseEntity<PresignedUrlResponse> getPresignedUrl(
        @AuthenticationPrincipal Long userId,
        PresignedUrlRequest request
    );

    @Operation(
        summary = "프로필 업데이트",
        description = """
            사용자의 프로필 정보를 업데이트합니다.

            **이미지 업로드 방법:**
            1. POST /api/users/profile-image/presigned-url로 업로드 URL 받기
            2. 받은 presignedUrl로 이미지를 S3에 직접 PUT 업로드
            3. 이 API 호출 시 fileUrl을 profileImageUrl에 포함

            **모든 필드 선택:** 전송되지 않은 필드는 기존 값이 유지됩니다.
            """,
        security = @SecurityRequirement(name = "Bearer Authentication"),
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "프로필 업데이트 성공",
                content = @Content(
                    schema = @Schema(implementation = UserResponse.class)
                )
            ),
            @ApiResponse(
                responseCode = "400",
                description = "잘못된 요청 (파일 형식 오류, 파일 크기 초과 등)",
                content = @Content(
                    schema = @Schema(implementation = ErrorResponse.class)
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
    ResponseEntity<UserResponse> updateProfile(
        @AuthenticationPrincipal Long userId,
        UpdateProfileRequest request
    );
}
