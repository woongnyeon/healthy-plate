package com.healthy_plate.shared.error.exception;

import lombok.Getter;

@Getter
public class S3Exception extends RuntimeException {

    private final ErrorCode errorCode;

    public S3Exception(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
