package net.lishen.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Author：li.shen
 * @bolg：
 * @Package：net.joseph.config
 * @Project：gpcloud-meter
 * @name：MinIOConfig
 * @Date：2024-01-20 22:02
 * @Filename：MinIOConfig
 */
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinIOConfig {
    // 端点
    private String endpoint;

    // 访问密钥
    private String accessKey;

    // 秘密密钥
    private String secretKey;

    // 存储桶名称
    private String bucketName;
    @Bean
    public MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
