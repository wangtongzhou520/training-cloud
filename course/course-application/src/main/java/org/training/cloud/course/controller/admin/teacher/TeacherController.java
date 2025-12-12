package org.training.cloud.course.controller.admin.teacher;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.teacher.AddTeacherDTO;
import org.training.cloud.course.dto.teacher.BindTeacherDTO;
import org.training.cloud.course.dto.teacher.ModifyTeacherDTO;
import org.training.cloud.course.dto.teacher.TeacherDTO;
import org.training.cloud.course.dto.teacher.UpdateTeacherRoleDTO;
import org.training.cloud.course.service.teacher.CourseTeacherRelationService;
import org.training.cloud.course.service.teacher.TeacherService;
import org.training.cloud.course.vo.teacher.CourseTeacherVO;
import org.training.cloud.course.vo.teacher.TeacherDetailVO;
import org.training.cloud.course.vo.teacher.TeacherVO;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台讲师")
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Resource
    private TeacherService teacherService;

    @Resource
    private CourseTeacherRelationService courseTeacherRelationService;

    @PostMapping("/add")
    @Operation(summary = "添加讲师")
    public CommonResponse<?> addTeacher(@RequestBody @Valid AddTeacherDTO addTeacherDTO) {
        teacherService.addTeacher(addTeacherDTO);
        return CommonResponse.ok();
    }

    @PutMapping("/update")
    @Operation(summary = "修改讲师信息")
    public CommonResponse<?> updateTeacher(@RequestBody @Valid ModifyTeacherDTO modifyTeacherDTO) {
        teacherService.modifyTeacher(modifyTeacherDTO);
        return CommonResponse.ok();
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询讲师")
    public CommonResponse<PageResponse<TeacherVO>> pageInfo(@Valid TeacherDTO teacherDTO) {
        PageResponse<TeacherVO> pageResponse = teacherService.pageInfo(teacherDTO);
        return CommonResponse.ok(pageResponse);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取讲师详情")
    public CommonResponse<TeacherDetailVO> getInfoById(@PathVariable("id") Long id) {
        TeacherDetailVO detail = teacherService.getTeacherDetail(id);
        return CommonResponse.ok(detail);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除讲师")
    public CommonResponse<?> delTeacher(@PathVariable("id") Long id) {
        teacherService.delTeacher(id);
        return CommonResponse.ok();
    }

    @PutMapping("/{id}/audit")
    @Operation(summary = "审核讲师")
    public CommonResponse<?> auditTeacher(
            @PathVariable("id") Long id,
            @RequestParam("approved") Boolean approved,
            @RequestParam(value = "auditOpinion", required = false) String auditOpinion) {
        teacherService.auditTeacher(id, approved, auditOpinion);
        return CommonResponse.ok();
    }

    @PutMapping("/{id}/status")
    @Operation(summary = "启用/禁用讲师")
    public CommonResponse<?> updateStatus(
            @PathVariable("id") Long id,
            @RequestParam("enabled") Boolean enabled) {
        teacherService.updateTeacherStatus(id, enabled);
        return CommonResponse.ok();
    }

    @PostMapping("/course/bind")
    @Operation(summary = "绑定讲师到课程")
    public CommonResponse<?> bindTeacher(@RequestBody @Valid BindTeacherDTO bindTeacherDTO) {
        courseTeacherRelationService.bindTeacher(bindTeacherDTO);
        return CommonResponse.ok();
    }

    @DeleteMapping("/course/{courseId}/unbind/{teacherId}")
    @Operation(summary = "解绑讲师")
    public CommonResponse<?> unbindTeacher(
            @PathVariable("courseId") Long courseId,
            @PathVariable("teacherId") Long teacherId) {
        courseTeacherRelationService.unbindTeacher(courseId, teacherId);
        return CommonResponse.ok();
    }

    @GetMapping("/course/{courseId}/teachers")
    @Operation(summary = "查询课程讲师列表")
    public CommonResponse<List<CourseTeacherVO>> getCourseTeachers(@PathVariable("courseId") Long courseId) {
        List<CourseTeacherVO> teachers = courseTeacherRelationService.getCourseTeachers(courseId);
        return CommonResponse.ok(teachers);
    }

    @PutMapping("/course/teacher/role")
    @Operation(summary = "更新讲师角色")
    public CommonResponse<?> updateTeacherRole(@RequestBody @Valid UpdateTeacherRoleDTO updateTeacherRoleDTO) {
        courseTeacherRelationService.updateTeacherRole(updateTeacherRoleDTO);
        return CommonResponse.ok();
    }

}
