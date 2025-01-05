package org.training.cloud.common.file.core.client;

/**
 * 文件服务端
 *
 * @author wangtongzhou
 * @since 2025-01-05 15:14
 */
public interface FileStorageService {

    String uploadFile(byte[] content,String path, String bucketName);
    void downloadFile(String fileName, String bucketName, String destinationPath);
    void deleteFile(String fileName, String bucketName);
}
