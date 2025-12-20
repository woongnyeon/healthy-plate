package com.healthy_plate.auth.infrastructure.oauth2;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
public class OAuth2LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Value("${app.auth.failure-url}")
    private String failureRedirectUrl;

    @Override
    public void onAuthenticationFailure(
        final HttpServletRequest request,
        final HttpServletResponse response,
        final AuthenticationException exception
    ) throws IOException {

        log.error("OAuth2 로그인 실패", exception);
        String redirectUrl = UriComponentsBuilder.fromUriString(failureRedirectUrl)
            .queryParam("error", "oauth2_failed")
            .queryParam("message", "소셜 로그인에 실패했습니다")
            .build()
            .toUriString();

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
