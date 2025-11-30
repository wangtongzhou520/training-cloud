package org.training.cloud.course.service.teacher;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.dao.teacher.CourseTeacherRelationMapper;
import org.training.cloud.course.dao.teacher.TeacherInfoMapper;
import org.training.cloud.course.dto.teacher.AddTeacherDTO;
import org.training.cloud.course.dto.teacher.ModifyTeacherDTO;
import org.training.cloud.course.entity.teacher.TeacherInfo;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 讲师服务单元测试
 *
 * @author wangtongzhou
 * @since 2025-11-30
 */
@RunWith(MockitoJUnitRunner.class)
public class TeacherServiceImplTest {

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @Mock
    private TeacherInfoMapper teacherInfoMapper;

    @Mock
    private CourseTeacherRelationMapper courseTeacherRelationMapper;

    private TeacherInfo testTeacher;

    @Before
    public void setUp() {
        testTeacher = new TeacherInfo();
        testTeacher.setId(1L);
        testTeacher.setUserId(100L);
        testTeacher.setRealName("测试讲师");
        testTeacher.setTeacherTitle("高级讲师");
        testTeacher.setStatus("APPROVED");
        testTeacher.setIsCertified(true);
        testTeacher.setTotalCourseCount(5);
        testTeacher.setTotalStudentCount(100);
        testTeacher.setAverageRating(BigDecimal.valueOf(4.5));
        testTeacher.setDeleteState(false);
    }

    /**
     * 测试新增讲师 - 成功场景
     */
    @Test
    public void testAddTeacher_Success() {
        // Given
        AddTeacherDTO addTeacherDTO = new AddTeacherDTO();
        addTeacherDTO.setUserId(200L);
        addTeacherDTO.setRealName("新讲师");
        addTeacherDTO.setTeacherTitle("初级讲师");
        addTeacherDTO.setDeleteState(false);

        when(teacherInfoMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(teacherInfoMapper.insert(any(TeacherInfo.class))).thenReturn(1);

        // When
        teacherService.addTeacher(addTeacherDTO);

        // Then
        verify(teacherInfoMapper, times(1)).insert(any(TeacherInfo.class));
    }

    /**
     * 测试新增讲师 - 用户ID已存在
     */
    @Test(expected = BusinessException.class)
    public void testAddTeacher_UserIdExists() {
        // Given
        AddTeacherDTO addTeacherDTO = new AddTeacherDTO();
        addTeacherDTO.setUserId(100L);
        addTeacherDTO.setRealName("新讲师");
        addTeacherDTO.setDeleteState(false);

        when(teacherInfoMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L);

        // When
        teacherService.addTeacher(addTeacherDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改讲师 - 成功场景
     */
    @Test
    public void testModifyTeacher_Success() {
        // Given
        ModifyTeacherDTO modifyTeacherDTO = new ModifyTeacherDTO();
        modifyTeacherDTO.setId(1L);
        modifyTeacherDTO.setRealName("修改后的讲师");
        modifyTeacherDTO.setDeleteState(false);

        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        teacherService.modifyTeacher(modifyTeacherDTO);

        // Then
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

    /**
     * 测试删除讲师 - 成功场景
     */
    @Test
    public void testDelTeacher_Success() {
        // Given
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(courseTeacherRelationMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(teacherInfoMapper.deleteById(1L)).thenReturn(1);

        // When
        teacherService.delTeacher(1L);

        // Then
        verify(teacherInfoMapper, times(1)).deleteById(1L);
    }

    /**
     * 测试删除讲师 - 已关联课程
     */
    @Test(expected = BusinessException.class)
    public void testDelTeacher_HasCourses() {
        // Given
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(courseTeacherRelationMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(3L);

        // When
        teacherService.delTeacher(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试审核讲师 - 通过审核
     */
    @Test
    public void testAuditTeacher_Approved() {
        // Given
        testTeacher.setStatus("PENDING");
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        teacherService.auditTeacher(1L, true, "审核通过");

        // Then
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

    /**
     * 测试审核讲师 - 拒绝审核
     */
    @Test
    public void testAuditTeacher_Rejected() {
        // Given
        testTeacher.setStatus("PENDING");
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        teacherService.auditTeacher(1L, false, "资料不全");

        // Then
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

    /**
     * 测试审核讲师 - 已经审核通过
     */
    @Test(expected = BusinessException.class)
    public void testAuditTeacher_AlreadyApproved() {
        // Given
        testTeacher.setStatus("APPROVED");
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);

        // When
        teacherService.auditTeacher(1L, true, "审核通过");

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试启用讲师 - 成功场景
     */
    @Test
    public void testUpdateTeacherStatus_Enable() {
        // Given
        testTeacher.setStatus("DISABLED");
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        teacherService.updateTeacherStatus(1L, true);

        // Then
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

    /**
     * 测试禁用讲师 - 成功场景
     */
    @Test
    public void testUpdateTeacherStatus_Disable() {
        // Given
        testTeacher.setStatus("APPROVED");
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);
        when(teacherInfoMapper.updateById(any(TeacherInfo.class))).thenReturn(1);

        // When
        teacherService.updateTeacherStatus(1L, false);

        // Then
        verify(teacherInfoMapper, times(1)).updateById(any(TeacherInfo.class));
    }

    /**
     * 测试获取讲师信息 - 成功场景
     */
    @Test
    public void testGetTeacherById_Success() {
        // Given
        when(teacherInfoMapper.selectById(1L)).thenReturn(testTeacher);

        // When
        TeacherInfo result = teacherService.getTeacherById(1L);

        // Then
        assertNotNull(result);
        assertEquals(testTeacher.getId(), result.getId());
        assertEquals(testTeacher.getRealName(), result.getRealName());
    }

    /**
     * 测试获取讲师信息 - 讲师不存在
     */
    @Test(expected = BusinessException.class)
    public void testGetTeacherById_NotExists() {
        // Given
        when(teacherInfoMapper.selectById(999L)).thenReturn(null);

        // When
        teacherService.getTeacherById(999L);

        // Then - 期望抛出BusinessException
    }

}
