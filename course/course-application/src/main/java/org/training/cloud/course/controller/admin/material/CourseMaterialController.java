package org.training.cloud.course.controller.admin.material;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.material.AddCourseMaterialDTO;
import org.training.cloud.course.dto.material.CourseMaterialDTO;
import org.training.cloud.course.dto.material.ModifyCourseMaterialDTO;
import org.training.cloud.course.service.material.CourseMaterialService;
import org.training.cloud.course.vo.material.CourseMaterialVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台课程资料")
@RestController
@RequestMapping("/material")
public class CourseMaterialController {

    @Resource
    private CourseMaterialService courseMaterialService;

    @PostMapping("/add")
    @Operation(summary = "添加课程资料")
    public CommonResponse<?> addCourseMaterial(@RequestBody @Valid AddCourseMaterialDTO addCourseMaterialDTO) {
        courseMaterialService.addCourseMaterial(addCourseMaterialDTO);
        return CommonResponse.ok();
    }

    @PutMapping("/update")
    @Operation(summary = "修改课程资料")
    public CommonResponse<?> updateCourseMaterial(@RequestBody @Valid ModifyCourseMaterialDTO modifyCourseMaterialDTO) {
        courseMaterialService.modifyCourseMaterial(modifyCourseMaterialDTO);
        return CommonResponse.ok();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询课程资料")
    public CommonResponse<PageResponse<CourseMaterialVO>> pageInfo(@Valid CourseMaterialDTO courseMaterialDTO) {
        PageResponse<CourseMaterialVO> pageResponse = courseMaterialService.pageInfo(courseMaterialDTO);
        return CommonResponse.ok(pageResponse);
    }

    @GetMapping("/list")
    @Operation(summary = "查询课程或课时资料列表")
    public CommonResponse<List<CourseMaterialVO>> listByCourseAndLesson(
            @RequestParam("courseId") Long courseId,
            @RequestParam(value = "lessonId", required = false) Long lessonId) {
        List<CourseMaterialVO> materials = courseMaterialService.listByCourseAndLesson(courseId, lessonId);
        return CommonResponse.ok(materials);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程资料详情")
    public CommonResponse<CourseMaterialVO> getInfoById(@PathVariable("id") Long id) {
        CourseMaterialVO detail = courseMaterialService.getCourseMaterialDetail(id);
        return CommonResponse.ok(detail);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除课程资料")
    public CommonResponse<?> delCourseMaterial(@PathVariable("id") Long id) {
        courseMaterialService.delCourseMaterial(id);
        return CommonResponse.ok();
    }

    @PutMapping("/{id}/download")
    @Operation(summary = "增加资料下载次数")
    public CommonResponse<?> increaseDownloadCount(@PathVariable("id") Long id) {
        courseMaterialService.increaseDownloadCount(id);
        return CommonResponse.ok();
    }

}
