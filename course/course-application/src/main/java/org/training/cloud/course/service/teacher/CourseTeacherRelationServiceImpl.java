package org.training.cloud.course.service.teacher;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.convert.teacher.CourseTeacherRelationConvert;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dao.teacher.CourseTeacherRelationMapper;
import org.training.cloud.course.dao.teacher.TeacherInfoMapper;
import org.training.cloud.course.dto.teacher.BindTeacherDTO;
import org.training.cloud.course.dto.teacher.UpdateTeacherRoleDTO;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.entity.teacher.CourseTeacherRelation;
import org.training.cloud.course.entity.teacher.TeacherInfo;
import org.training.cloud.course.vo.teacher.CourseTeacherVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;


@Service
public class CourseTeacherRelationServiceImpl implements CourseTeacherRelationService {

    @Resource
    private CourseTeacherRelationMapper courseTeacherRelationMapper;

    @Resource
    private TeacherInfoMapper teacherInfoMapper;

    @Resource
    private CourseMapper courseMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindTeacher(BindTeacherDTO bindTeacherDTO) {
        // 检查课程是否存在
        Course course = courseMapper.selectById(bindTeacherDTO.getCourseId());
        if (Objects.isNull(course)) {
            throw new BusinessException(COURSE_NOT_EXISTS);
        }

        // 检查讲师是否存在
        TeacherInfo teacherInfo = teacherInfoMapper.selectById(bindTeacherDTO.getTeacherId());
        if (Objects.isNull(teacherInfo)) {
            throw new BusinessException(TEACHER_NOT_EXISTS);
        }

        // 检查讲师状态
        if ("DISABLED".equals(teacherInfo.getStatus())) {
            throw new BusinessException(TEACHER_STATUS_DISABLED);
        }

        // 检查是否已经绑定
        Long count = courseTeacherRelationMapper.selectCount(
                new LambdaQueryWrapperExtend<CourseTeacherRelation>()
                        .eq(CourseTeacherRelation::getCourseId, bindTeacherDTO.getCourseId())
                        .eq(CourseTeacherRelation::getTeacherId, bindTeacherDTO.getTeacherId())
        );
        if (count > 0) {
            throw new BusinessException(COURSE_TEACHER_ALREADY_EXISTS);
        }

        // 创建关联
        CourseTeacherRelation relation = CourseTeacherRelationConvert.INSTANCE.convert(bindTeacherDTO);
        courseTeacherRelationMapper.insert(relation);

        // 更新讲师课程数量统计
        updateTeacherCourseCount(bindTeacherDTO.getTeacherId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbindTeacher(Long courseId, Long teacherId) {
        // 查询关联关系
        CourseTeacherRelation relation = courseTeacherRelationMapper.selectOne(
                new LambdaQueryWrapperExtend<CourseTeacherRelation>()
                        .eq(CourseTeacherRelation::getCourseId, courseId)
                        .eq(CourseTeacherRelation::getTeacherId, teacherId)
        );
        if (Objects.isNull(relation)) {
            throw new BusinessException(COURSE_TEACHER_RELATION_NOT_EXISTS);
        }

        // 删除关联
        courseTeacherRelationMapper.deleteById(relation.getId());

        // 更新讲师课程数量统计
        updateTeacherCourseCount(teacherId);
    }

    @Override
    public List<CourseTeacherVO> getCourseTeachers(Long courseId) {
        // 查询课程讲师关联
        List<CourseTeacherRelation> relations = courseTeacherRelationMapper.selectList(
                new LambdaQueryWrapperExtend<CourseTeacherRelation>()
                        .eq(CourseTeacherRelation::getCourseId, courseId)
                        .orderByAsc(CourseTeacherRelation::getSort)
        );

        if (relations == null || relations.isEmpty()) {
            return new ArrayList<>();
        }

        // 查询讲师信息
        List<Long> teacherIds = relations.stream()
                .map(CourseTeacherRelation::getTeacherId)
                .collect(Collectors.toList());

        List<TeacherInfo> teachers = teacherInfoMapper.selectBatchIds(teacherIds);
        Map<Long, TeacherInfo> teacherMap = teachers.stream()
                .collect(Collectors.toMap(TeacherInfo::getId, t -> t));

        // 组装结果
        return relations.stream()
                .map(relation -> {
                    CourseTeacherVO vo = CourseTeacherRelationConvert.INSTANCE.convert(relation);
                    TeacherInfo teacher = teacherMap.get(relation.getTeacherId());
                    if (teacher != null) {
                        vo.setRealName(teacher.getRealName());
                        vo.setTeacherTitle(teacher.getTeacherTitle());
                        vo.setAvatarUrl(teacher.getAvatarUrl());
                        vo.setIntroduction(teacher.getIntroduction());
                    }
                    return vo;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacherRole(UpdateTeacherRoleDTO updateTeacherRoleDTO) {
        // 查询关联关系
        CourseTeacherRelation relation = courseTeacherRelationMapper.selectOne(
                new LambdaQueryWrapperExtend<CourseTeacherRelation>()
                        .eq(CourseTeacherRelation::getCourseId, updateTeacherRoleDTO.getCourseId())
                        .eq(CourseTeacherRelation::getTeacherId, updateTeacherRoleDTO.getTeacherId())
        );
        if (Objects.isNull(relation)) {
            throw new BusinessException(COURSE_TEACHER_RELATION_NOT_EXISTS);
        }

        // 更新角色和分成比例
        relation.setTeacherRole(updateTeacherRoleDTO.getTeacherRole());
        relation.setRevenueShareRate(updateTeacherRoleDTO.getRevenueShareRate());
        courseTeacherRelationMapper.updateById(relation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTeacherCourseCount(Long teacherId) {
        // 统计讲师的课程数量
        Long count = courseTeacherRelationMapper.selectCount(
                new LambdaQueryWrapperExtend<CourseTeacherRelation>()
                        .eq(CourseTeacherRelation::getTeacherId, teacherId)
        );

        // 更新讲师统计信息
        TeacherInfo teacherInfo = new TeacherInfo();
        teacherInfo.setId(teacherId);
        teacherInfo.setTotalCourseCount(count.intValue());
        teacherInfoMapper.updateById(teacherInfo);
    }

}
