package org.training.cloud.tool.controller.admin.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.file.core.client.FileStorageProperties;
import org.training.cloud.common.file.core.client.FileStorageService;
import org.training.cloud.tool.convert.file.FileConvert;
import org.training.cloud.tool.dto.file.AddFileDTO;
import org.training.cloud.tool.dto.file.FileDTO;
import org.training.cloud.tool.entity.file.File;
import org.training.cloud.tool.service.file.FileService;
import org.training.cloud.tool.vo.file.FileVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;

/**
 * 文件管理
 *
 * @author wangtongzhou
 * @since 2024-04-21 21:40
 */

@Tag(name = "管理后台文件")
@RestController
@RequestMapping("/tool/file")
public class FileController {

    @Resource
    private FileService fileService;

    @Resource
    private FileStorageService fileStorageService;

    @Resource
    private FileStorageProperties fileStorageProperties;


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("categoryId") Long categoryId,
                             @RequestParam("file") MultipartFile file) {
        try {
            byte[] binaryData = file.getBytes();
            String fileName = file.getOriginalFilename();
            //文件后缀
            String fileExtension = "";
            if (fileName != null && fileName.lastIndexOf(".") > 0) {
                fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
            }
            long fileSize = file.getSize();
            String filePath = System.currentTimeMillis() + "." + fileExtension;
            String url = fileStorageService.uploadFile(binaryData,
                    filePath, fileStorageProperties.getBucketName());
            AddFileDTO addFileDTO = new AddFileDTO();
            addFileDTO.setType(fileExtension);
            addFileDTO.setName(fileName);
            addFileDTO.setPath(filePath);
            addFileDTO.setCategoryId(categoryId);
            addFileDTO.setSize(fileSize);
            addFileDTO.setUrl(url);
            fileService.addFile(addFileDTO);
            return url;
        } catch (IOException e) {
            return "Failed to upload file: " + e.getMessage();
        }
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询文件")
    public CommonResponse<PageResponse<FileVO>> pageFile(@Valid FileDTO fileDTO) {
        PageResponse<File> filePageResponse = fileService.pageFile(fileDTO);
        return CommonResponse.ok(FileConvert.INSTANCE.convert(filePageResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文件信息")
    public CommonResponse<?> getFileById(@PathVariable("id") Long id) {
        File file = fileService.getFileById(id);
        return CommonResponse.ok(FileConvert.INSTANCE.convert(file));
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除文件")
    public CommonResponse<?> delFile(@PathVariable("id") Long id) {
        fileService.delFile(id);
        return CommonResponse.ok();
    }
}
