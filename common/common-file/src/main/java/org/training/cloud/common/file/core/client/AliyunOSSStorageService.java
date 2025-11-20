package org.training.cloud.common.file.core.client;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.File;

/**
 * 阿里云上传服务
 *
 * @author wangtongzhou
 * @since 2025-01-05 15:43
 */
@Service
public class AliyunOSSStorageService implements FileStorageService {
    @Resource
    private FileStorageProperties properties;

    @Override
    public String uploadFile(byte[] content, String path, String bucketName) {
        OSS ossClient = new OSSClientBuilder()
                .build(properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());
        try {
            ossClient.putObject(bucketName, path, new ByteArrayInputStream(content));
            return "https://"+bucketName+ "." + properties.getEndpoint() +
                    "/" + path;
        } finally {
            ossClient.shutdown();
        }
    }

    @Override
    public void downloadFile(String fileName, String bucketName, String destinationPath) {
        OSS ossClient = new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());
        try {
            ossClient.getObject(new GetObjectRequest(bucketName, fileName), new File(destinationPath));
        } finally {
            ossClient.shutdown();
        }

    }

    @Override
    public void deleteFile(String fileName, String bucketName) {
        OSS ossClient = new OSSClientBuilder().build(properties.getEndpoint(), properties.getAccessKey(), properties.getSecretKey());
        try {
            ossClient.deleteObject(bucketName, fileName);
        } finally {
            ossClient.shutdown();
        }
    }


}
