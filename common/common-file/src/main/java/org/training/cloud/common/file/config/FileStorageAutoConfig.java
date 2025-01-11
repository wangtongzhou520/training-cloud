package org.training.cloud.common.file.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.training.cloud.common.file.core.client.AliyunOSSStorageService;
import org.training.cloud.common.file.core.client.FileStorageProperties;
import org.training.cloud.common.file.core.client.FileStorageService;
import org.training.cloud.common.file.core.client.LocalFileStorageService;

import javax.annotation.Resource;

/**
 * 文件配置
 *
 * @author wangtongzhou
 * @since 2025-01-05 14:33
 */
@AutoConfiguration
@EnableConfigurationProperties(FileStorageProperties.class)
public class FileStorageAutoConfig {
    @Resource
    private FileStorageProperties properties;

    @Bean
    @ConditionalOnProperty(name = "file.storage.provider", havingValue = "aliyun")
    public FileStorageService aliyunOSSStorageService() {
        return new AliyunOSSStorageService();
    }

    @Bean
    @ConditionalOnProperty(name = "file.storage.provider", havingValue = "local")
    public FileStorageService localFileStorageService() {
        return new LocalFileStorageService();
    }
}
