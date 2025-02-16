package org.training.cloud.course.controller.admin.course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.course.CourseConvert;
import org.training.cloud.course.dto.course.AddCourseDTO;
import org.training.cloud.course.dto.course.CourseDTO;
import org.training.cloud.course.dto.course.ModifyCourseDTO;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.service.course.CourseService;
import org.training.cloud.course.vo.course.CourseVO;

import javax.annotation.Resource;
import javax.validation.Valid;


@Tag(name = "管理后台课程")
@RestController
@RequestMapping("/course")
public class CourseController {
    @Resource
    private CourseService courseService;

    @PostMapping("/add")
    @Operation(summary = "添加课程信息")
    public CommonResponse<?> addCourse(@RequestBody @Valid AddCourseDTO addCourseDTO) {
        courseService.addCourse(addCourseDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/update")
    @Operation(summary = "更新课程信息")
    public CommonResponse<?> updateCourse(@RequestBody @Valid ModifyCourseDTO modifyCourseDTO) {
        courseService.modifyCourse(modifyCourseDTO);
        return CommonResponse.ok();
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询课程")
    public CommonResponse<PageResponse<CourseVO>> pageInfo(@Valid CourseDTO courseDTO) {
        PageResponse<Course> coursePageResponse =
                courseService.pageInfo(courseDTO);
        return CommonResponse.ok(CourseConvert.INSTANCE.convert(coursePageResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程信息")
    public CommonResponse<?> getInfoById(@PathVariable("id") Long id) {
        Course info = courseService.getCourseById(id);
        return CommonResponse.ok(CourseConvert.INSTANCE.convert(info));
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除课程")
    public CommonResponse<?> delCourse(@PathVariable("id") Long id) {
        courseService.delCourse(id);
        return CommonResponse.ok();
    }
}