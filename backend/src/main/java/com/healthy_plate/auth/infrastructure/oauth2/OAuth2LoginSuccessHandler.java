package com.healthy_plate.auth.infrastructure.oauth2;

import com.healthy_plate.auth.domain.model.JwtProperties;
import com.healthy_plate.auth.domain.model.JwtTokenProvider;
import com.healthy_plate.auth.domain.service.RefreshTokenService;
import com.healthy_plate.auth.infrastructure.util.CookieUtil;
import com.healthy_plate.user.domain.model.User;
import com.healthy_plate.user.domain.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final String REFRESH_TOKEN_NAME = "refresh_token";

    @Value("${app.auth.success-url}")
    private String targetUrl;

    @Value("${app.cookie.secure}")
    private boolean cookieSecure;

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtProperties jwtProperties;
    private final RefreshTokenService refreshTokenService;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final Authentication authentication
    ) throws IOException {

        final CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

        log.info("OAuth2 로그인 성공 - userId: {}, email: {}", oauth2User.getUserId(), oauth2User.getEmail());

        final User user = userRepository.findById(oauth2User.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        final String refreshToken = jwtTokenProvider.generateRefreshToken(oauth2User.getUserId());

        refreshTokenService.saveRefreshToken(oauth2User.getUserId(), refreshToken, jwtProperties.refreshTokenExpiration());
        addRefreshTokenCookies(response, refreshToken);

        final String redirectUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("isFirstLogin", user.isFirstLogin())
            .build()
            .toUriString();

        log.info("리다이렉트 URL: {}", redirectUrl);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }


    private void addRefreshTokenCookies(
        final HttpServletResponse response,
        final String refreshToken
    ) {
        Cookie refreshTokenCookie = CookieUtil.createCookie(
            REFRESH_TOKEN_NAME,
            refreshToken,
            (int) (jwtProperties.refreshTokenExpiration() / 1000),
            cookieSecure
        );
        response.addCookie(refreshTokenCookie);
        log.info("토큰을 쿠키에 추가했습니다");
    }

}
