package com.healthy_plate.shared.error.exception;

import org.springframework.http.HttpStatus;

public enum S3ErrorCode implements ErrorCode {

    UNSUPPORTED_FILE_TYPE("F001", "지원하지 않는 파일 형식입니다", HttpStatus.BAD_REQUEST),
    FILE_SIZE_EXCEEDED("F002", "파일 크기가 제한을 초과했습니다", HttpStatus.BAD_REQUEST),
    FILE_UPLOAD_FAILED("F003", "파일 업로드에 실패했습니다", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;

    S3ErrorCode(String code, String message, HttpStatus status) {
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
