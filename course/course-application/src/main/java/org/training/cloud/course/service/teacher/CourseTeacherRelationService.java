package org.training.cloud.course.service.teacher;


import org.training.cloud.course.dto.teacher.BindTeacherDTO;
import org.training.cloud.course.dto.teacher.UpdateTeacherRoleDTO;
import org.training.cloud.course.vo.teacher.CourseTeacherVO;

import java.util.List;


public interface CourseTeacherRelationService {

    /**
     * 绑定讲师到课程
     *
     * @param bindTeacherDTO 绑定信息
     */
    void bindTeacher(BindTeacherDTO bindTeacherDTO);

    /**
     * 解绑讲师
     *
     * @param courseId 课程ID
     * @param teacherId 讲师ID
     */
    void unbindTeacher(Long courseId, Long teacherId);

    /**
     * 查询课程讲师列表
     *
     * @param courseId 课程ID
     * @return 讲师列表
     */
    List<CourseTeacherVO> getCourseTeachers(Long courseId);

    /**
     * 更新讲师角色
     *
     * @param updateTeacherRoleDTO 更新信息
     */
    void updateTeacherRole(UpdateTeacherRoleDTO updateTeacherRoleDTO);

    /**
     * 更新讲师课程数量统计
     *
     * @param teacherId 讲师ID
     */
    void updateTeacherCourseCount(Long teacherId);

}
