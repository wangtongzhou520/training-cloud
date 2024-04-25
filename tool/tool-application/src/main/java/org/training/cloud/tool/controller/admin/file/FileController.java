package org.training.cloud.tool.controller.admin.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.convert.file.FileConvert;
import org.training.cloud.tool.dto.file.*;
import org.training.cloud.tool.entity.file.File;
import org.training.cloud.tool.service.file.FileService;
import org.training.cloud.tool.vo.file.FileVO;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

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

    @PostMapping("/add")
    @Operation(summary = "添加文件信息")
    public CommonResponse<?> addFile(@RequestBody @Valid AddFileDTO addFileDTO) {
        fileService.addFile(addFileDTO);
        return CommonResponse.ok();
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询文件")
    public CommonResponse<PageResponse<FileVO>> pageFile (@Valid FileDTO fileDTO) {
        PageResponse<File> filePageResponse =fileService.pageFile(fileDTO);
        return CommonResponse.ok(FileConvert.INSTANCE.convert(filePageResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取文件信息")
    public CommonResponse<?> getFileById(@PathVariable("id") Long id) {
        File file=fileService.getFileById(id);
        return CommonResponse.ok(FileConvert.INSTANCE.convert(file));
    }



    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除文件")
    public CommonResponse<?> delFile(@PathVariable("id") Long id) {
        fileService.delFile(id);
        return CommonResponse.ok();
    }
}
