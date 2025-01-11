package org.training.cloud.common.file.core.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 文件存储的配置
 *
 * @author wangtongzhou
 * @since 2025-01-05 15:34
 */
@ConfigurationProperties(prefix = "file.storage")
public class FileStorageProperties {

    /**
     * 提供服务商
     */
    private String provider;

    private String accessKey;

    private String secretKey;

    /**
     * 地址
     */
    private String endpoint;
    /**
     * 存储桶名称
     */
    private String bucketName;


    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
}

