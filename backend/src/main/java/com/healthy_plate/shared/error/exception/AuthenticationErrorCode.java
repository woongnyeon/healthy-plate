package com.healthy_plate.shared.error.exception;

import org.springframework.http.HttpStatus;

public enum AuthenticationErrorCode implements ErrorCode {

    LOGIN_REQUIRED_SERVICE("A101", "로그인이 필요한 서비스입니다", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("A102", "유효하지 않은 Refresh Token 입니다", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_NOT_FOUND("A103", "Refresh Token을 찾을 수 없습니다", HttpStatus.UNAUTHORIZED),
    EXPIRED_REFRESH_TOKEN("A104", "만료된 Refresh Token입니다", HttpStatus.UNAUTHORIZED),
    ALREADY_REGISTERED_NICKNAME("A105", "이미 등록된 사용자입니다", HttpStatus.BAD_REQUEST),
    OAUTH2_LOGIN_FAILED("A106", "소셜 로그인에 실패했습니다", HttpStatus.UNAUTHORIZED),
    EXPIRED_ACCESS_TOKEN("A107", "만료된 Access Token 입니다.", HttpStatus.UNAUTHORIZED),
    INVALID_ACCESS_TOKEN("A108", "유효하지 않은 Access Token 입니다.", HttpStatus.UNAUTHORIZED),
    PROFILE_REGISTRATION_REQUIRED("A109", "프로필 등록이 필요합니다", HttpStatus.FORBIDDEN);

    private final String code;
    private final String message;
    private final HttpStatus status;

    AuthenticationErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }
}
