package com.healthy_plate.shared.s3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {

    @Bean
    public S3Presigner s3Presigner(final S3Client s3Client) {
        return S3Presigner.builder()
            .region(s3Client.serviceClientConfiguration().region())
            .credentialsProvider(s3Client.serviceClientConfiguration().credentialsProvider())
            .build();
    }
}
