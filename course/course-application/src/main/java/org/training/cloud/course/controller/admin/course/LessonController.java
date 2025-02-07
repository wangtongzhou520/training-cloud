package org.training.cloud.course.controller.admin.course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.course.LessonConvert;
import org.training.cloud.course.dto.course.AddLessonDTO;
import org.training.cloud.course.dto.course.LessonDTO;
import org.training.cloud.course.dto.course.ModifyLessonDTO;
import org.training.cloud.course.entity.course.Lesson;
import org.training.cloud.course.service.course.LessonService;
import org.training.cloud.course.vo.course.LessonVO;

import javax.annotation.Resource;
import javax.validation.Valid;


@Tag(name = "管理后台课程内容")
@RestController
@RequestMapping("/course/lesson")
public class LessonController {
    @Resource
    private LessonService lessonService;

    @PostMapping("/add")
    @Operation(summary = "添加课程内容信息")
    public CommonResponse<?> addLesson(@RequestBody @Valid AddLessonDTO addLessonDTO) {
        lessonService.addLesson(addLessonDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/update")
    @Operation(summary = "更新课程内容信息")
    public CommonResponse<?> updateLesson(@RequestBody @Valid ModifyLessonDTO modifyLessonDTO) {
        lessonService.modifyLesson(modifyLessonDTO);
        return CommonResponse.ok();
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询课程内容")
    public CommonResponse<PageResponse<LessonVO>> pageLesson (@Valid LessonDTO lessonDTO) {
        PageResponse<Lesson> lessonPageResponse =lessonService.pageLesson(lessonDTO);
        return CommonResponse.ok(LessonConvert.INSTANCE.convert(lessonPageResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程内容信息")
    public CommonResponse<?> getLessonById(@PathVariable("id") Long id) {
        Lesson lesson=lessonService.getLessonById(id);
        return CommonResponse.ok(LessonConvert.INSTANCE.convert(lesson));
    }



    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除课程内容")
    public CommonResponse<?> delLesson(@PathVariable("id") Long id) {
        lessonService.delLesson(id);
        return CommonResponse.ok();
    }
}