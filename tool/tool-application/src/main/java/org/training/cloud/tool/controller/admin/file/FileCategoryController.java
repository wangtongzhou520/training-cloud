package org.training.cloud.tool.controller.admin.file;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.tool.dto.file.AddFileCategoryDTO;
import org.training.cloud.tool.dto.file.FileCategoryDTO;
import org.training.cloud.tool.dto.file.ModifyFileCategoryDTO;
import org.training.cloud.tool.service.file.FileCategoryService;
import org.training.cloud.tool.vo.file.FileCategoryVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台文件分类")
@RestController
@RequestMapping("/tool")
public class FileCategoryController {
    @Resource
    private FileCategoryService fileCategoryService;

    @PostMapping("/file-category")
    @Operation(summary = "添加文件分类信息")
    public CommonResponse<?> addFileCategory(@RequestBody @Valid AddFileCategoryDTO addFileCategoryDTO) {
        fileCategoryService.addFileCategory(addFileCategoryDTO);
        return CommonResponse.ok();
    }

    @PostMapping("/file-category/fileCategoryList")
    @Operation(summary = "文件分类")
    public CommonResponse<List<FileCategoryVO>> deptList(@RequestBody FileCategoryDTO fileCategoryDTO) {
        return CommonResponse.ok(fileCategoryService.getAllFileCategory(fileCategoryDTO));
    }


    @PutMapping("/file-category")
    @Operation(summary = "更新文件分类信息")
    public CommonResponse<?> updateFileCategory(@RequestBody @Valid ModifyFileCategoryDTO modifyFileCategoryDTO) {
        fileCategoryService.modifyFileCategory(modifyFileCategoryDTO);
        return CommonResponse.ok();
    }


//    @GetMapping("/page")
//    @Operation(summary = "分页查询文件分类")
//    public CommonResponse<PageResponse<FileCategoryVO>> pageFileCategory(@Valid FileCategoryDTO fileCategoryDTO) {
//        PageResponse<FileCategory> fileCategoryPageResponse = fileCategoryService.pageFileCategory(fileCategoryDTO);
//        return CommonResponse.ok(FileCategoryConvert.INSTANCE.convert(fileCategoryPageResponse));
//    }
//
//    @GetMapping("/{id}")
//    @Operation(summary = "获取文件分类信息")
//    public CommonResponse<?> getFileCategoryById(@PathVariable("id") Long id) {
//        FileCategory fileCategory = fileCategoryService.getFileCategoryById(id);
//        return CommonResponse.ok(FileCategoryConvert.INSTANCE.convert(fileCategory));
//    }


    @DeleteMapping("/file-category/{id}")
    @Operation(summary = "删除文件分类")
    public CommonResponse<?> delFileCategory(@PathVariable("id") Long id) {
        fileCategoryService.delFileCategory(id);
        return CommonResponse.ok();
    }
}