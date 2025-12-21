package com.healthy_plate.auth.infrastructure.util;

import jakarta.servlet.http.Cookie;
import java.util.Arrays;

public class CookieUtil {

    private static final String REFRESH_TOKEN_NAME = "refresh_token";

    private CookieUtil() {
    }

    public static Cookie createCookie(final String name, final String value, final int maxAge, final boolean secure) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setAttribute("SameSite", "Lax");
        return cookie;
    }

    public static String findRefreshTokenWithCookie(final Cookie[] cookies) {
        return Arrays.stream(cookies)
            .filter(cookie -> cookie.getName().equals(REFRESH_TOKEN_NAME))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Refresh Token 쿠키를 찾을 수 없습니다."))
            .getValue();
    }

    public static Cookie deleteCookie(final String name) {
        return createCookie(name, "", 0, true);
    }
}