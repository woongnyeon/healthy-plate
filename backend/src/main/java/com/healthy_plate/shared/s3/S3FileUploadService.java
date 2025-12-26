package com.healthy_plate.shared.s3;

import java.time.Duration;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3FileUploadService {

    private final S3Presigner s3Presigner;
    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.cloud.aws.s3.folders.profile}")
    private String profileFolder;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    //Presigned URL을 생성합니다.
    public PresignedUrlResponse getPreSignedUrl(final String prefix) {
        final String key = profileFolder + prefix + "/" + UUID.randomUUID() + ".jpg";

        final PutObjectRequest putObjectRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType("image/jpeg")
            .build();

        final PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
            .putObjectRequest(putObjectRequest)
            .signatureDuration(Duration.ofMinutes(10))
            .build();

        final PresignedPutObjectRequest presignedRequest = s3Presigner.presignPutObject(presignRequest);

        final String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, key);

        log.info("Generated presigned URL for key: {}", key);
        return new PresignedUrlResponse(presignedRequest.url().toString(), fileUrl);
    }

    //S3에서 파일을 삭제합니다.
    public void deleteFile(final String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }

        try {
            final String key = extractKeyFromUrl(fileUrl);

            final DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

            s3Client.deleteObject(deleteRequest);
            log.info("File deleted successfully: {}", key);
        } catch (Exception e) {
            log.warn("Failed to delete file from S3: {}", fileUrl, e);
        }
    }

    //URL에서 S3 key 추출
    private String extractKeyFromUrl(final String fileUrl) {
        // https://bucket.s3.region.amazonaws.com/key 형식에서 key 추출
        final String prefix = String.format("https://%s.s3.%s.amazonaws.com/", bucketName, region);
        if (fileUrl.startsWith(prefix)) {
            return fileUrl.substring(prefix.length());
        }
        throw new IllegalArgumentException("유효하지 않은 S3 URL입니다.");
    }
}
