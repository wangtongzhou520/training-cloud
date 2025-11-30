package org.training.cloud.course.service.teacher;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.teacher.AddTeacherDTO;
import org.training.cloud.course.dto.teacher.ModifyTeacherDTO;
import org.training.cloud.course.dto.teacher.TeacherDTO;
import org.training.cloud.course.entity.teacher.TeacherInfo;
import org.training.cloud.course.vo.teacher.TeacherDetailVO;
import org.training.cloud.course.vo.teacher.TeacherVO;


public interface TeacherService {

    /**
     * 添加讲师
     *
     * @param addTeacherDTO 讲师信息
     */
    void addTeacher(AddTeacherDTO addTeacherDTO);

    /**
     * 修改讲师
     *
     * @param modifyTeacherDTO 讲师信息
     */
    void modifyTeacher(ModifyTeacherDTO modifyTeacherDTO);

    /**
     * 分页查询讲师
     *
     * @param teacherDTO 查询条件
     * @return 分页结果
     */
    PageResponse<TeacherVO> pageInfo(TeacherDTO teacherDTO);

    /**
     * 查询讲师详情
     *
     * @param id 讲师ID
     * @return 讲师详情
     */
    TeacherDetailVO getTeacherDetail(Long id);

    /**
     * 删除讲师
     *
     * @param id 讲师ID
     */
    void delTeacher(Long id);

    /**
     * 审核讲师
     *
     * @param id 讲师ID
     * @param approved 是否通过（true-通过，false-拒绝）
     * @param auditOpinion 审核意见
     */
    void auditTeacher(Long id, Boolean approved, String auditOpinion);

    /**
     * 启用/禁用讲师
     *
     * @param id 讲师ID
     * @param enabled 是否启用（true-启用，false-禁用）
     */
    void updateTeacherStatus(Long id, Boolean enabled);

    /**
     * 根据ID获取讲师信息
     *
     * @param id 讲师ID
     * @return 讲师信息
     */
    TeacherInfo getTeacherById(Long id);

}
