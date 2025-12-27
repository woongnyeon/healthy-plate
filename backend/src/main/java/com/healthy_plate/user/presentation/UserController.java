package com.healthy_plate.user.presentation;

import com.healthy_plate.shared.s3.AllowedImageType;
import com.healthy_plate.shared.s3.PresignedUrlRequest;
import com.healthy_plate.shared.s3.PresignedUrlResponse;
import com.healthy_plate.shared.s3.S3FileUploadService;
import com.healthy_plate.user.application.UserService;
import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.presentation.dto.UpdateProfileRequest;
import com.healthy_plate.user.presentation.dto.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController implements SwaggerUserController {

    private final UserService userService;
    private final S3FileUploadService s3FileUploadService;

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal final Long userId) {
        final User user = userService.findUser(userId);
        return ResponseEntity.ok(UserResponse.from(user));
    }

    @GetMapping("/duplicate/{nickname}")
    public ResponseEntity<Boolean> isDuplicatedNickname(@PathVariable final String nickname) {
        final boolean duplicated = userService.isDuplicatedNickname(nickname);
        return ResponseEntity.ok(duplicated);
    }

    @PostMapping("/profile-image/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(
        @AuthenticationPrincipal final Long userId,
        @Valid @RequestBody final PresignedUrlRequest request
    ) {
        final PresignedUrlResponse response = s3FileUploadService.getPreSignedUrl(
            String.valueOf(userId),
            AllowedImageType.fromContentType(request.contentType()),
            request.fileSize()
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
        @AuthenticationPrincipal final Long userId,
        @Valid @RequestBody final UpdateProfileRequest request
    ) {
        final User updatedUser = userService.updateUserProfile(
            userId,
            request.nickname(),
            request.profileImageUrl(),
            request.introduction()
        );
        return ResponseEntity.ok(UserResponse.from(updatedUser));
    }
}