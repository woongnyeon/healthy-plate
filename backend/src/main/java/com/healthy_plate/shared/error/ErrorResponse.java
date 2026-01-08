package com.healthy_plate.shared.error;

import com.healthy_plate.shared.error.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;

@Schema(description = "오류 응답")
@Getter
public class ErrorResponse {

    @Schema(description = "HTTP 상태 코드")
    private final int status;

    @Schema(description = "오류 코드 (A1xx: 인증/인가, B1xx: 비즈니스)")
    private final String code;

    @Schema(description = "오류 메시지")
    private final String message;

    @Schema(description = "요청 HTTP 메서드")
    private final String method;

    @Schema(description = "요청 경로")
    private final String path;

    @Schema(description = "오류 발생 시간")
    private final LocalDateTime timestamp;

    public ErrorResponse(int status, String code, String message, String method, String path) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.method = method;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(int status, ErrorCode errorCode, String method, String path) {
        this.status = status;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.method = method;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }
}
