package com.healthy_plate.shared.s3;

public record PresignedUrlResponse(
    String presignedUrl,
    String fileUrl
) {
}