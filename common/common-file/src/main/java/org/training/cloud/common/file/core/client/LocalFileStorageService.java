package org.training.cloud.common.file.core.client;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 本地文件
 *
 * @author wangtongzhou
 * @since 2025-01-05 16:18
 */
@Service
public class LocalFileStorageService implements FileStorageService {
    @Resource
    private FileStorageProperties properties;

    @Override
    public String uploadFile(byte[] content, String path, String bucketName) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            fos.write(content);
        } catch (IOException e) {
            throw new RuntimeException("upload file failed", e);
        }
        return properties.getEndpoint() + "/" + path;
    }

    @Override
    public void downloadFile(String fileName, String bucketName, String destinationPath) {
        try {
            File source = new File(fileName);
            File destination = new File(destinationPath);
            FileUtils.copyFile(source, destination);
        } catch (IOException e) {
            throw new RuntimeException("download file failed", e);
        }
    }

    @Override
    public void deleteFile(String fileName, String bucketName) {
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
