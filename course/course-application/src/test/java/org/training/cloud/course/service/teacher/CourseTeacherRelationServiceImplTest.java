package org.training.cloud.course.service.teacher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dao.teacher.CourseTeacherRelationMapper;
import org.training.cloud.course.dao.teacher.TeacherInfoMapper;
import org.training.cloud.course.dto.teacher.BindTeacherDTO;
import org.training.cloud.course.dto.teacher.UpdateTeacherRoleDTO;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.entity.teacher.CourseTeacherRelation;
import org.training.cloud.course.entity.teacher.TeacherInfo;
import org.training.cloud.course.vo.teacher.CourseTeacherVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

/**
 * 课程讲师关联服务单元测试
 *
 * @author wangtongzhou
 * @since 2025-11-30
 */
@RunWith(MockitoJUnitRunner.class)
public class CourseTeacherRelationServiceImplTest {

    @InjectMocks
    private CourseTeacherRelationServiceImpl courseTeacherRelationService;

    @Mock
    private CourseTeacherRelationMapper courseTeacherRelationMapper;

    @Mock
    private TeacherInfoMapper teacherInfoMapper;

    @Mock
    private CourseMapper courseMapper;

    private Course testCourse;
    private TeacherInfo testTeacher;
    private CourseTeacherRelation testRelation;

    @Before
    public void setUp() {
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setCourseName("测试课程");

        testTeacher = new TeacherInfo();
        testTeacher.setId(1L);
        testTeacher.setUserId(100L);
        testTeacher.setRealName("测试讲师");
        testTeacher.setTeacherTitle("高级讲师");
        testTeacher.setStatus("APPROVED");
        testTeacher.setTotalCourseCount(5);

        testRelation = new CourseTeacherRelation();
        testRelation.setId(1L);
        testRelation.setCourseId(1L);
        testRelation.setTeacherId(1L);
        testRelation.setTeacherRole("MAIN");
        testRelation.setRevenueShareRate(BigDecimal.valueOf(0.7));
        testRelation.setSort(1);
    }

    /**
     * 测试绑定讲师 - 成功场景
     */
    @Test
    public void testBindTeacher_Success() {
        // Given
        BindTeacherDTO bindTeacherDTO = new BindTeacherDTO();
        bindTeacherDTO.setCourseId(1L);
        bindTeacherDTO.setTeacherId(1L);
        bindTeacherDTO.setTeacherRole("MAIN");
        bindTeacherDTO.setRevenueShareRate(BigDecimal.valueOf(0.7));
        bindTeacherDTO.setSort(1);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(courseTeacherRelationMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(courseTeacherRelationMapper.insert(any(CourseTeacherRelation.class))).thenReturn(1);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        courseTeacherRelationService.bindTeacher(bindTeacherDTO);

        // Then
        verify(courseTeacherRelationMapper, times(1)).insert(any(CourseTeacherRelation.class));
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

    /**
     * 测试绑定讲师 - 课程不存在
     */
    @Test(expected = BusinessException.class)
    public void testBindTeacher_CourseNotExists() {
        // Given
        BindTeacherDTO bindTeacherDTO = new BindTeacherDTO();
        bindTeacherDTO.setCourseId(999L);
        bindTeacherDTO.setTeacherId(1L);
        bindTeacherDTO.setTeacherRole("MAIN");
        bindTeacherDTO.setSort(1);

        when(courseMapper.selectById(999L)).thenReturn(null);

        // When
        courseTeacherRelationService.bindTeacher(bindTeacherDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试绑定讲师 - 讲师不存在
     */
    @Test(expected = BusinessException.class)
    public void testBindTeacher_TeacherNotExists() {
        // Given
        BindTeacherDTO bindTeacherDTO = new BindTeacherDTO();
        bindTeacherDTO.setCourseId(1L);
        bindTeacherDTO.setTeacherId(999L);
        bindTeacherDTO.setTeacherRole("MAIN");
        bindTeacherDTO.setSort(1);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(teacherInfoMapper.selectById(999L)).thenReturn(null);

        // When
        courseTeacherRelationService.bindTeacher(bindTeacherDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试绑定讲师 - 讲师已被禁用
     */
    @Test(expected = BusinessException.class)
    public void testBindTeacher_TeacherDisabled() {
        // Given
        BindTeacherDTO bindTeacherDTO = new BindTeacherDTO();
        bindTeacherDTO.setCourseId(1L);
        bindTeacherDTO.setTeacherId(1L);
        bindTeacherDTO.setTeacherRole("MAIN");
        bindTeacherDTO.setSort(1);

        testTeacher.setStatus("DISABLED");

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);

        // When
        courseTeacherRelationService.bindTeacher(bindTeacherDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试绑定讲师 - 已经绑定
     */
    @Test(expected = BusinessException.class)
    public void testBindTeacher_AlreadyExists() {
        // Given
        BindTeacherDTO bindTeacherDTO = new BindTeacherDTO();
        bindTeacherDTO.setCourseId(1L);
        bindTeacherDTO.setTeacherId(1L);
        bindTeacherDTO.setTeacherRole("MAIN");
        bindTeacherDTO.setSort(1);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(courseTeacherRelationMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L);

        // When
        courseTeacherRelationService.bindTeacher(bindTeacherDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试解绑讲师 - 成功场景
     */
    @Test
    public void testUnbindTeacher_Success() {
        // Given
        when(courseTeacherRelationMapper.selectOne(any(LambdaQueryWrapperExtend.class))).thenReturn(testRelation);
        when(courseTeacherRelationMapper.deleteById(1L)).thenReturn(1);
        when(courseTeacherRelationMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(4L);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        courseTeacherRelationService.unbindTeacher(1L, 1L);

        // Then
        verify(courseTeacherRelationMapper, times(1)).deleteById(1L);
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

    /**
     * 测试解绑讲师 - 关联关系不存在
     */
    @Test(expected = BusinessException.class)
    public void testUnbindTeacher_RelationNotExists() {
        // Given
        when(courseTeacherRelationMapper.selectOne(any(LambdaQueryWrapperExtend.class))).thenReturn(null);

        // When
        courseTeacherRelationService.unbindTeacher(1L, 1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试查询课程讲师列表 - 成功场景
     */
    @Test
    public void testGetCourseTeachers_Success() {
        // Given
        List<CourseTeacherRelation> relations = Arrays.asList(testRelation);
        List<TeacherInfo> teachers = Arrays.asList(testTeacher);

        when(courseTeacherRelationMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(relations);
        when(teacherInfoMapper.selectBatchIds(anyList())).thenReturn(teachers);

        // When
        List<CourseTeacherVO> result = courseTeacherRelationService.getCourseTeachers(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testTeacher.getRealName(), result.get(0).getRealName());
    }

    /**
     * 测试查询课程讲师列表 - 无讲师
     */
    @Test
    public void testGetCourseTeachers_Empty() {
        // Given
        when(courseTeacherRelationMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(new ArrayList<>());

        // When
        List<CourseTeacherVO> result = courseTeacherRelationService.getCourseTeachers(1L);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * 测试更新讲师角色 - 成功场景
     */
    @Test
    public void testUpdateTeacherRole_Success() {
        // Given
        UpdateTeacherRoleDTO updateDTO = new UpdateTeacherRoleDTO();
        updateDTO.setCourseId(1L);
        updateDTO.setTeacherId(1L);
        updateDTO.setTeacherRole("ASSISTANT");
        updateDTO.setRevenueShareRate(BigDecimal.valueOf(0.3));

        when(courseTeacherRelationMapper.selectOne(any(LambdaQueryWrapperExtend.class))).thenReturn(testRelation);
        when(courseTeacherRelationMapper.updateById(any(CourseTeacherRelation.class))).thenReturn(1);

        // When
        courseTeacherRelationService.updateTeacherRole(updateDTO);

        // Then
        verify(courseTeacherRelationMapper, times(1)).updateById(any(CourseTeacherRelation.class));
    }

    /**
     * 测试更新讲师角色 - 关联关系不存在
     */
    @Test(expected = BusinessException.class)
    public void testUpdateTeacherRole_RelationNotExists() {
        // Given
        UpdateTeacherRoleDTO updateDTO = new UpdateTeacherRoleDTO();
        updateDTO.setCourseId(1L);
        updateDTO.setTeacherId(999L);
        updateDTO.setTeacherRole("ASSISTANT");

        when(courseTeacherRelationMapper.selectOne(any(LambdaQueryWrapperExtend.class))).thenReturn(null);

        // When
        courseTeacherRelationService.updateTeacherRole(updateDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试更新讲师课程数量统计
     */
    @Test
    public void testUpdateTeacherCourseCount() {
        // Given
        when(courseTeacherRelationMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(10L);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        courseTeacherRelationService.updateTeacherCourseCount(1L);

        // Then
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

}
