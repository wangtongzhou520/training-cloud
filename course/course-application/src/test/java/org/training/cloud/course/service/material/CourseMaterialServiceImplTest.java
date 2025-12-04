package org.training.cloud.course.service.material;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dao.course.LessonMapper;
import org.training.cloud.course.dao.material.CourseMaterialMapper;
import org.training.cloud.course.dto.material.AddCourseMaterialDTO;
import org.training.cloud.course.dto.material.ModifyCourseMaterialDTO;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.entity.course.Lesson;
import org.training.cloud.course.entity.material.CourseMaterial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 课程资料服务单元测试
 *
 * @author wangtongzhou
 * @since 2025-12-04
 */
@RunWith(MockitoJUnitRunner.class)
public class CourseMaterialServiceImplTest {

    @InjectMocks
    private CourseMaterialServiceImpl courseMaterialService;

    @Mock
    private CourseMaterialMapper courseMaterialMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private LessonMapper lessonMapper;

    private CourseMaterial testMaterial;
    private Course testCourse;
    private Lesson testLesson;

    @Before
    public void setUp() {
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setCourseName("测试课程");

        testLesson = new Lesson();
        testLesson.setId(1L);
        testLesson.setLessonName("测试课时");
        testLesson.setChapterId(1L);

        testMaterial = new CourseMaterial();
        testMaterial.setId(1L);
        testMaterial.setCourseId(1L);
        testMaterial.setLessonId(null);
        testMaterial.setMaterialName("测试资料");
        testMaterial.setMaterialType("PDF");
        testMaterial.setMaterialUrl("https://example.com/material.pdf");
        testMaterial.setFileSize(1024000L);
        testMaterial.setDownloadCount(0);
        testMaterial.setIsFree(true);
        testMaterial.setSort(1);
        testMaterial.setDeleteState(false);
    }

    /**
     * 测试新增课程资料 - 成功场景
     */
    @Test
    public void testAddCourseMaterial_Success() {
        // Given
        AddCourseMaterialDTO addDTO = new AddCourseMaterialDTO();
        addDTO.setCourseId(1L);
        addDTO.setMaterialName("新资料");
        addDTO.setMaterialType("PDF");
        addDTO.setMaterialUrl("https://example.com/new.pdf");
        addDTO.setFileSize(2048000L);
        addDTO.setIsFree(true);
        addDTO.setSort(1);
        addDTO.setDeleteState(false);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(courseMaterialMapper.insert(any(CourseMaterial.class))).thenReturn(1);

        // When
        courseMaterialService.addCourseMaterial(addDTO);

        // Then
        verify(courseMaterialMapper, times(1)).insert(any(CourseMaterial.class));
    }

    /**
     * 测试新增课程资料 - 课程不存在
     */
    @Test(expected = BusinessException.class)
    public void testAddCourseMaterial_CourseNotExists() {
        // Given
        AddCourseMaterialDTO addDTO = new AddCourseMaterialDTO();
        addDTO.setCourseId(999L);
        addDTO.setMaterialName("新资料");
        addDTO.setMaterialType("PDF");
        addDTO.setMaterialUrl("https://example.com/new.pdf");
        addDTO.setFileSize(2048000L);
        addDTO.setIsFree(true);
        addDTO.setSort(1);
        addDTO.setDeleteState(false);

        when(courseMapper.selectById(999L)).thenReturn(null);

        // When
        courseMaterialService.addCourseMaterial(addDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试新增课程资料（关联课时） - 成功场景
     */
    @Test
    public void testAddCourseMaterial_WithLesson_Success() {
        // Given
        AddCourseMaterialDTO addDTO = new AddCourseMaterialDTO();
        addDTO.setCourseId(1L);
        addDTO.setLessonId(1L);
        addDTO.setMaterialName("课时资料");
        addDTO.setMaterialType("PPT");
        addDTO.setMaterialUrl("https://example.com/lesson.ppt");
        addDTO.setFileSize(3072000L);
        addDTO.setIsFree(false);
        addDTO.setSort(2);
        addDTO.setDeleteState(false);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(lessonMapper.selectById(1L)).thenReturn(testLesson);
        when(courseMaterialMapper.insert(any(CourseMaterial.class))).thenReturn(1);

        // When
        courseMaterialService.addCourseMaterial(addDTO);

        // Then
        verify(courseMaterialMapper, times(1)).insert(any(CourseMaterial.class));
    }

    /**
     * 测试新增课程资料 - 课时不存在
     */
    @Test(expected = BusinessException.class)
    public void testAddCourseMaterial_LessonNotExists() {
        // Given
        AddCourseMaterialDTO addDTO = new AddCourseMaterialDTO();
        addDTO.setCourseId(1L);
        addDTO.setLessonId(999L);
        addDTO.setMaterialName("课时资料");
        addDTO.setMaterialType("PPT");
        addDTO.setMaterialUrl("https://example.com/lesson.ppt");
        addDTO.setFileSize(3072000L);
        addDTO.setIsFree(false);
        addDTO.setSort(2);
        addDTO.setDeleteState(false);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(lessonMapper.selectById(999L)).thenReturn(null);

        // When
        courseMaterialService.addCourseMaterial(addDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改课程资料 - 成功场景
     */
    @Test
    public void testModifyCourseMaterial_Success() {
        // Given
        ModifyCourseMaterialDTO modifyDTO = new ModifyCourseMaterialDTO();
        modifyDTO.setId(1L);
        modifyDTO.setMaterialName("修改后的资料");
        modifyDTO.setMaterialType("WORD");
        modifyDTO.setMaterialUrl("https://example.com/modified.docx");
        modifyDTO.setFileSize(1536000L);
        modifyDTO.setIsFree(true);
        modifyDTO.setSort(1);
        modifyDTO.setDeleteState(false);

        when(courseMaterialMapper.selectById(1L)).thenReturn(testMaterial);
        when(courseMaterialMapper.updateById(any(CourseMaterial.class))).thenReturn(1);

        // When
        courseMaterialService.modifyCourseMaterial(modifyDTO);

        // Then
        verify(courseMaterialMapper, times(1)).updateById(any(CourseMaterial.class));
    }

    /**
     * 测试修改课程资料 - 资料不存在
     */
    @Test(expected = BusinessException.class)
    public void testModifyCourseMaterial_NotExists() {
        // Given
        ModifyCourseMaterialDTO modifyDTO = new ModifyCourseMaterialDTO();
        modifyDTO.setId(999L);
        modifyDTO.setMaterialName("修改后的资料");
        modifyDTO.setMaterialType("WORD");
        modifyDTO.setMaterialUrl("https://example.com/modified.docx");
        modifyDTO.setFileSize(1536000L);
        modifyDTO.setIsFree(true);
        modifyDTO.setSort(1);
        modifyDTO.setDeleteState(false);

        when(courseMaterialMapper.selectById(999L)).thenReturn(null);

        // When
        courseMaterialService.modifyCourseMaterial(modifyDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试查询课程资料列表 - 查询课程资料
     */
    @Test
    public void testListByCourseAndLesson_CourseOnly() {
        // Given
        List<CourseMaterial> materials = Arrays.asList(testMaterial);
        when(courseMaterialMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(materials);

        // When
        courseMaterialService.listByCourseAndLesson(1L, null);

        // Then
        verify(courseMaterialMapper, times(1)).selectList(any(LambdaQueryWrapperExtend.class));
    }

    /**
     * 测试查询课程资料列表 - 查询课时资料
     */
    @Test
    public void testListByCourseAndLesson_WithLesson() {
        // Given
        testMaterial.setLessonId(1L);
        List<CourseMaterial> materials = Arrays.asList(testMaterial);
        when(courseMaterialMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(materials);

        // When
        courseMaterialService.listByCourseAndLesson(1L, 1L);

        // Then
        verify(courseMaterialMapper, times(1)).selectList(any(LambdaQueryWrapperExtend.class));
    }

    /**
     * 测试删除课程资料 - 成功场景
     */
    @Test
    public void testDelCourseMaterial_Success() {
        // Given
        when(courseMaterialMapper.selectById(1L)).thenReturn(testMaterial);
        when(courseMaterialMapper.deleteById(1L)).thenReturn(1);

        // When
        courseMaterialService.delCourseMaterial(1L);

        // Then
        verify(courseMaterialMapper, times(1)).deleteById(1L);
    }

    /**
     * 测试删除课程资料 - 资料不存在
     */
    @Test(expected = BusinessException.class)
    public void testDelCourseMaterial_NotExists() {
        // Given
        when(courseMaterialMapper.selectById(999L)).thenReturn(null);

        // When
        courseMaterialService.delCourseMaterial(999L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试增加下载次数 - 成功场景
     */
    @Test
    public void testIncreaseDownloadCount_Success() {
        // Given
        when(courseMaterialMapper.selectById(1L)).thenReturn(testMaterial);
        when(courseMaterialMapper.updateById(any(CourseMaterial.class))).thenReturn(1);

        // When
        courseMaterialService.increaseDownloadCount(1L);

        // Then
        verify(courseMaterialMapper, times(1)).updateById(any(CourseMaterial.class));
    }

    /**
     * 测试增加下载次数 - 资料不存在
     */
    @Test(expected = BusinessException.class)
    public void testIncreaseDownloadCount_NotExists() {
        // Given
        when(courseMaterialMapper.selectById(999L)).thenReturn(null);

        // When
        courseMaterialService.increaseDownloadCount(999L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试获取资料详情 - 成功场景
     */
    @Test
    public void testGetCourseMaterialDetail_Success() {
        // Given
        when(courseMaterialMapper.selectById(1L)).thenReturn(testMaterial);

        // When
        courseMaterialService.getCourseMaterialDetail(1L);

        // Then
        verify(courseMaterialMapper, times(1)).selectById(1L);
    }

    /**
     * 测试获取资料详情 - 资料不存在
     */
    @Test(expected = BusinessException.class)
    public void testGetCourseMaterialDetail_NotExists() {
        // Given
        when(courseMaterialMapper.selectById(999L)).thenReturn(null);

        // When
        courseMaterialService.getCourseMaterialDetail(999L);

        // Then - 期望抛出BusinessException
    }

}
