package com.healthy_plate.shared.s3;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PresignedUrlRequest(
    @NotBlank(message = "contentType은 필수입니다.")
    String contentType,

    @NotNull(message = "fileSize는 필수입니다.")
    @Max(value = MAX_FILE_SIZE, message = "파일 크기는 5MB 이하여야 합니다.")
    Long fileSize
) {

    private static final long MAX_FILE_SIZE = 5L * 1024 * 1024;
}