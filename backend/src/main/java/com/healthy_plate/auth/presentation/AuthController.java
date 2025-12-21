package com.healthy_plate.auth.presentation;

import com.healthy_plate.auth.application.AuthService;
import com.healthy_plate.auth.domain.model.JwtProperties;
import com.healthy_plate.auth.infrastructure.util.CookieUtil;
import com.healthy_plate.auth.presentation.dto.AuthResponse;
import com.healthy_plate.auth.presentation.dto.LoginSuccessResponse;
import com.healthy_plate.auth.presentation.dto.UpdateNicknameRequest;
import com.healthy_plate.user.domain.model.User;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    @PostMapping("/success")
    public ResponseEntity<LoginSuccessResponse> refreshToken(
        final HttpServletRequest request
    ) {
        String refreshToken = CookieUtil.findRefreshTokenWithCookie(request.getCookies());
        String newAccessToken = authService.generateAccessToken(refreshToken);

        return ResponseEntity.ok(LoginSuccessResponse.from(newAccessToken));
    }

    @GetMapping("/token")
    public ResponseEntity<AuthResponse> getAccessToken(
        final HttpServletRequest request
    ) {
        String refreshToken = CookieUtil.findRefreshTokenWithCookie(request.getCookies());
        String accessToken = authService.generateAccessToken(refreshToken);
        User user = authService.getUserFromRefreshToken(refreshToken);

        return ResponseEntity.ok(AuthResponse.of(accessToken, user));
    }

    @PatchMapping("/register")
    public ResponseEntity<AuthResponse> registerNickname(
        @Valid @RequestBody final UpdateNicknameRequest request,
        final HttpServletRequest httpRequest
    ) {
        String refreshToken = CookieUtil.findRefreshTokenWithCookie(httpRequest.getCookies());
        String accessToken = authService.registerNickname(refreshToken, request.nickname());
        User user = authService.getUserFromRefreshToken(refreshToken);

        return ResponseEntity.ok(AuthResponse.of(accessToken, user));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
        @CookieValue(name = "refreshToken") final String refreshToken,
        final HttpServletResponse response
    ) {
        authService.logout(refreshToken);

        Cookie refreshTokenCookie = CookieUtil.deleteCookie(REFRESH_TOKEN_NAME);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok().build();
    }
}
