package com.healthy_plate.auth.presentation;

import com.healthy_plate.auth.application.AuthService;
import com.healthy_plate.auth.domain.model.JwtProperties;
import com.healthy_plate.auth.infrastructure.util.CookieUtil;
import com.healthy_plate.auth.presentation.dto.TokenResponse;
import com.healthy_plate.auth.presentation.dto.RegisterUserProfileRequest;
import com.healthy_plate.shared.s3.PresignedUrlResponse;
import com.healthy_plate.shared.s3.S3FileUploadService;
import com.healthy_plate.user.domain.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController implements SwaggerAuthController {

    private static final String REFRESH_TOKEN_NAME = "refresh_token";

    private final AuthService authService;
    private final JwtProperties jwtProperties;
    private final S3FileUploadService s3FileUploadService;

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> getAccessToken(
        final HttpServletRequest request
    ) {
        String refreshToken = CookieUtil.findRefreshTokenWithCookie(request.getCookies());
        String newAccessToken = authService.generateAccessToken(refreshToken);

        return ResponseEntity.ok(new TokenResponse(newAccessToken));
    }

    //회원가입 용
    @PostMapping("/profile-image/presigned-url")
    public ResponseEntity<PresignedUrlResponse> getPresignedUrl(final HttpServletRequest httpRequest) {
        final String refreshToken = CookieUtil.findRefreshTokenWithCookie(httpRequest.getCookies());
        final User user = authService.getUserFromRefreshToken(refreshToken);

        final PresignedUrlResponse response = s3FileUploadService.getPreSignedUrl(String.valueOf(user.getId()));

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/register")
    public ResponseEntity<TokenResponse> registerUserInfo(
        @RequestBody final RegisterUserProfileRequest request,
        final HttpServletRequest httpRequest
    ) {
        String refreshToken = CookieUtil.findRefreshTokenWithCookie(httpRequest.getCookies());
        String accessToken = authService.registerUserInfo(
            refreshToken,
            request.nickname(),
            request.profileImageUrl(),
            request.introduction()
        );

        return ResponseEntity.ok(new TokenResponse(accessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @CookieValue(name = "refresh_token") final String refreshToken,
        final HttpServletResponse response
    ) {
        authService.logout(refreshToken);

        Cookie refreshTokenCookie = CookieUtil.deleteCookie(REFRESH_TOKEN_NAME);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().build();
    }
}
