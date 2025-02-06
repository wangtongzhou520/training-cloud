package org.training.cloud.course.controller.admin.course;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.course.ChapterConvert;
import org.training.cloud.course.dto.course.AddChapterDTO;
import org.training.cloud.course.dto.course.ChapterDTO;
import org.training.cloud.course.dto.course.ModifyChapterDTO;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.service.course.ChapterService;
import org.training.cloud.course.vo.course.ChapterVO;

import javax.annotation.Resource;
import javax.validation.Valid;


@Tag(name = "管理后台课程章节")
@RestController
@RequestMapping("/course/chapter")
public class ChapterController {
    @Resource
    private ChapterService chapterService;

    @PostMapping("/add")
    @Operation(summary = "添加课程章节信息")
    public CommonResponse<?> addChapter(@RequestBody @Valid AddChapterDTO addChapterDTO) {
        chapterService.addChapter(addChapterDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/update")
    @Operation(summary = "更新课程章节信息")
    public CommonResponse<?> updateChapter(@RequestBody @Valid ModifyChapterDTO modifyChapterDTO) {
        chapterService.modifyChapter(modifyChapterDTO);
        return CommonResponse.ok();
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询课程章节")
    public CommonResponse<PageResponse<ChapterVO>> pageChapter (@Valid ChapterDTO chapterDTO) {
        PageResponse<Chapter> chapterPageResponse =chapterService.pageChapter(chapterDTO);
        return CommonResponse.ok(ChapterConvert.INSTANCE.convert(chapterPageResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程章节信息")
    public CommonResponse<?> getChapterById(@PathVariable("id") Long id) {
        Chapter chapter=chapterService.getChapterById(id);
        return CommonResponse.ok(ChapterConvert.INSTANCE.convert(chapter));
    }



    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除课程章节")
    public CommonResponse<?> delChapter(@PathVariable("id") Long id) {
        chapterService.delChapter(id);
        return CommonResponse.ok();
    }
}