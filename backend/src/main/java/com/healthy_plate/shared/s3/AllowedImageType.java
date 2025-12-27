package com.healthy_plate.shared.s3;

import com.healthy_plate.shared.error.exception.S3ErrorCode;
import com.healthy_plate.shared.error.exception.S3Exception;
import lombok.Getter;

@Getter
public enum AllowedImageType {

    JPEG("jpg", "image/jpeg"),
    PNG("png", "image/png"),
    WEBP("webp", "image/webp");

    private final String extension;
    private final String contentType;

    AllowedImageType(String extension, String contentType) {
        this.extension = extension;
        this.contentType = contentType;
    }

    public static AllowedImageType fromContentType(String contentType) {
        for (AllowedImageType type : values()) {
            if (type.contentType.equals(contentType)) {
                return type;
            }
        }
        throw new S3Exception(S3ErrorCode.UNSUPPORTED_FILE_TYPE);
    }
}
