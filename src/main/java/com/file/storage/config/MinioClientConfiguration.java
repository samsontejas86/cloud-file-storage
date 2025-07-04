package com.file.storage.config;

import io.minio.MinioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MinioClientConfiguration {

    private final MinioClientProperties minioClientProperties;

    @Bean
    public MinioClient configuredMinioClient() {
        return MinioClient.builder()
                .endpoint(minioClientProperties.getEndpoint())
                .credentials(minioClientProperties.getUser(), minioClientProperties.getPassword())
                .build();
    }
}
