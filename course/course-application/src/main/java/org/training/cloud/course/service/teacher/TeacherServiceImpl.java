package org.training.cloud.course.service.teacher;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.convert.teacher.TeacherConvert;
import org.training.cloud.course.dao.teacher.CourseTeacherRelationMapper;
import org.training.cloud.course.dao.teacher.TeacherInfoMapper;
import org.training.cloud.course.dto.teacher.AddTeacherDTO;
import org.training.cloud.course.dto.teacher.ModifyTeacherDTO;
import org.training.cloud.course.dto.teacher.TeacherDTO;
import org.training.cloud.course.entity.teacher.CourseTeacherRelation;
import org.training.cloud.course.entity.teacher.TeacherInfo;
import org.training.cloud.course.vo.teacher.TeacherDetailVO;
import org.training.cloud.course.vo.teacher.TeacherVO;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;


@Service
public class TeacherServiceImpl implements TeacherService {

    @Resource
    private TeacherInfoMapper teacherInfoMapper;

    @Resource
    private CourseTeacherRelationMapper courseTeacherRelationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addTeacher(AddTeacherDTO addTeacherDTO) {
        // 检查用户ID是否已存在
        Long count = teacherInfoMapper.selectCount(
                new LambdaQueryWrapperExtend<TeacherInfo>()
                        .eq(TeacherInfo::getUserId, addTeacherDTO.getUserId())
                        .eq(TeacherInfo::getDeleteState, false)
        );
        if (count > 0) {
            throw new BusinessException(TEACHER_USER_ID_EXISTS);
        }

        // 转换并初始化讲师信息
        TeacherInfo teacherInfo = TeacherConvert.INSTANCE.convert(addTeacherDTO);
        teacherInfo.setStatus("PENDING"); // 默认待审核
        teacherInfo.setTotalStudentCount(0);
        teacherInfo.setTotalCourseCount(0);
        teacherInfo.setAverageRating(BigDecimal.ZERO);
        teacherInfo.setTotalRevenue(BigDecimal.ZERO);

        teacherInfoMapper.insert(teacherInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyTeacher(ModifyTeacherDTO modifyTeacherDTO) {
        checkExistById(modifyTeacherDTO.getId());

        TeacherInfo teacherInfo = TeacherConvert.INSTANCE.convert(modifyTeacherDTO);
        teacherInfoMapper.updateById(teacherInfo);
    }

    @Override
    public PageResponse<TeacherVO> pageInfo(TeacherDTO teacherDTO) {
        PageResponse<TeacherInfo> pageResponse = teacherInfoMapper.selectPage(teacherDTO);
        return TeacherConvert.INSTANCE.convert(pageResponse);
    }

    @Override
    public TeacherDetailVO getTeacherDetail(Long id) {
        TeacherInfo teacherInfo = getTeacherById(id);
        return TeacherConvert.INSTANCE.convertToDetail(teacherInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delTeacher(Long id) {
        TeacherInfo teacherInfo = getTeacherById(id);

        // 检查讲师是否已关联课程
        Long count = courseTeacherRelationMapper.selectCount(
                new LambdaQueryWrapperExtend<CourseTeacherRelation>()
                        .eq(CourseTeacherRelation::getTeacherId, id)
        );
        if (count > 0) {
            throw new BusinessException(TEACHER_HAS_COURSES);
        }

        teacherInfoMapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditTeacher(Long id, Boolean approved, String auditOpinion) {
        TeacherInfo teacherInfo = getTeacherById(id);

        // 检查讲师状态
        if ("APPROVED".equals(teacherInfo.getStatus())) {
            throw new BusinessException(TEACHER_ALREADY_APPROVED);
        }
        if ("REJECTED".equals(teacherInfo.getStatus())) {
            throw new BusinessException(TEACHER_ALREADY_REJECTED);
        }

        // 更新审核状态
        TeacherInfo updateTeacher = new TeacherInfo();
        updateTeacher.setId(id);
        updateTeacher.setStatus(approved ? "APPROVED" : "REJECTED");
        updateTeacher.setAuditOpinion(auditOpinion);

        teacherInfoMapper.updateById(updateTeacher);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacherStatus(Long id, Boolean enabled) {
        TeacherInfo teacherInfo = getTeacherById(id);

        // 检查讲师是否已审核通过
        if (!"APPROVED".equals(teacherInfo.getStatus()) && !"DISABLED".equals(teacherInfo.getStatus())) {
            throw new BusinessException(new org.training.cloud.common.core.constant.ExceptionCode(
                    103005007, "只有已审核通过或已禁用的讲师才能修改启用状态"));
        }

        // 更新状态
        TeacherInfo updateTeacher = new TeacherInfo();
        updateTeacher.setId(id);
        updateTeacher.setStatus(enabled ? "APPROVED" : "DISABLED");

        teacherInfoMapper.updateById(updateTeacher);
    }

    @Override
    public TeacherInfo getTeacherById(Long id) {
        TeacherInfo teacherInfo = teacherInfoMapper.selectById(id);
        if (Objects.isNull(teacherInfo)) {
            throw new BusinessException(TEACHER_NOT_EXISTS);
        }
        return teacherInfo;
    }

    /**
     * 校验讲师是否存在
     *
     * @param id 讲师ID
     */
    private void checkExistById(Long id) {
        TeacherInfo teacherInfo = teacherInfoMapper.selectById(id);
        if (Objects.isNull(teacherInfo)) {
            throw new BusinessException(TEACHER_NOT_EXISTS);
        }
    }

}
