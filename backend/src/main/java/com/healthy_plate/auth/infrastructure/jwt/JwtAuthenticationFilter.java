package com.healthy_plate.auth.infrastructure.jwt;

import com.healthy_plate.auth.domain.model.JwtTokenProvider;
import com.healthy_plate.shared.error.exception.AuthenticationErrorCode;
import com.healthy_plate.user.domain.model.UserRole;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String JWT_ERROR_CODE_ATTRIBUTE = "jwt_error_code";

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
        @NonNull final HttpServletRequest request,
        @NonNull final HttpServletResponse response,
        @NonNull final FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            final String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt)) {
                // 토큰이 있으면 검증
                final Long userId = jwtTokenProvider.getUserIdFromToken(jwt);
                final UserRole role = jwtTokenProvider.getRoleFromToken(jwt);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userId,
                    null,
                    Collections.singleton(new SimpleGrantedAuthority(role.name()))
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("JWT 인증 성공 - userId: {}, role: {}", userId, role);
            }
            // 토큰이 없으면 인증 없이 진행 (public 엔드포인트를 위해)

        } catch (ExpiredJwtException e) {
            log.warn("만료된 액세스 토큰 - URI: {} {}", request.getMethod(), request.getRequestURI());
            request.setAttribute(JWT_ERROR_CODE_ATTRIBUTE, AuthenticationErrorCode.EXPIRED_ACCESS_TOKEN);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.warn(
                "유효하지 않은 액세스 토큰 - URI: {} {}, 원인: {}",
                request.getMethod(), request.getRequestURI(), e.getClass().getSimpleName()
            );
            request.setAttribute(JWT_ERROR_CODE_ATTRIBUTE, AuthenticationErrorCode.INVALID_ACCESS_TOKEN);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(BEARER_PREFIX.length());
        }
        return null;
    }
}
