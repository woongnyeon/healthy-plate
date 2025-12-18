package com.healthy_plate.auth.infrastructure.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oauth2User = (CustomOAuth2User) authentication.getPrincipal();

        log.info("OAuth2 로그인 성공 - userId: {}, email: {}", oauth2User.getUserId(), oauth2User.getEmail());

        // TODO: JWT 토큰 발급 후 프론트엔드로 리다이렉트
        // 임시로 메인 페이지로 리다이렉트
        getRedirectStrategy().sendRedirect(request, response, "/");
    }
}
