package com.healthy_plate.shared.error;

import com.healthy_plate.shared.error.exception.BusinessException;
import com.healthy_plate.shared.error.exception.CustomAuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomAuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
        final CustomAuthenticationException e,
        final HttpServletRequest request
    ) {
        log.warn("Authentication Exception - URI '{} {}' ", request.getMethod(), request.getRequestURI(), e);
        final HttpStatus httpStatus = e.getErrorCode().getStatus();
        final ErrorResponse errorResponse = new ErrorResponse(
            httpStatus.value(),
            e.getErrorCode(),
            request.getMethod(),
            request.getRequestURI()
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(
        final BusinessException e,
        final HttpServletRequest request
    ) {
        log.warn("Business Exception - URI '{} {}' ", request.getMethod(), request.getRequestURI(), e);
        final HttpStatus httpStatus = e.getErrorCode().getStatus();
        final ErrorResponse errorResponse = new ErrorResponse(
            httpStatus.value(),
            e.getErrorCode(),
            request.getMethod(),
            request.getRequestURI()
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(
        final IllegalArgumentException e,
        final HttpServletRequest request
    ) {
        log.warn("Illegal Argument Exception - URI '{} {}', 메시지: {}",
            request.getMethod(), request.getRequestURI(), e.getMessage());
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        final ErrorResponse errorResponse = new ErrorResponse(
            httpStatus.value(),
            "BAD_REQUEST",
            e.getMessage(),
            request.getMethod(),
            request.getRequestURI()
        );
        return ResponseEntity.status(httpStatus).body(errorResponse);
    }
}
