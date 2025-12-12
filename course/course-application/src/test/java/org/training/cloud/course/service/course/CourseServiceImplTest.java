package org.training.cloud.course.service.course;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.dao.category.CategoryMapper;
import org.training.cloud.course.dao.course.ChapterMapper;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dto.course.AddCourseDTO;
import org.training.cloud.course.dto.course.ModifyCourseDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.entity.course.Lesson;
import org.training.cloud.course.vo.course.CourseDetailVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 课程服务单元测试
 *
 * @author wangtongzhou
 * @since 2025-01-29
 */
@RunWith(MockitoJUnitRunner.class)
public class CourseServiceImplTest {

    @InjectMocks
    private CourseServiceImpl courseService;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private CategoryMapper categoryMapper;

    @Mock
    private ChapterMapper chapterMapper;

    @Mock
    private ChapterService chapterService;

    @Mock
    private LessonService lessonService;

    private Course testCourse;
    private Category testCategory;
    private Chapter testChapter;
    private Lesson testLesson;

    @Before
    public void setUp() {
        testCategory = new Category();
        testCategory.setId(1L);
        testCategory.setCategoryName("测试分类");

        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setCourseName("测试课程");
        testCourse.setCategoryId(1L);
        testCourse.setCourseDescription("课程描述");
        testCourse.setIsPublished(false);
        testCourse.setChapterState(0);
        testCourse.setDeleteState(false);

        testChapter = new Chapter();
        testChapter.setId(1L);
        testChapter.setChapterName("第一章");
        testChapter.setCourseId(1L);
        testChapter.setSort(1);

        testLesson = new Lesson();
        testLesson.setId(1L);
        testLesson.setLessonName("第一课时");
        testLesson.setChapterId(1L);
        testLesson.setSort(1);
        testLesson.setLessonUrl("https://example.com/lesson1.mp4");
    }

    /**
     * 测试新增课程 - 成功场景
     */
    @Test
    public void testAddCourse_Success() {
        // Given
        AddCourseDTO addCourseDTO = new AddCourseDTO();
        addCourseDTO.setCourseName("新课程");
        addCourseDTO.setCategoryId(1L);
        addCourseDTO.setCourseDescription("新课程描述");

        when(categoryMapper.selectById(1L)).thenReturn(testCategory);
        when(courseMapper.insert(any(Course.class))).thenReturn(1);

        // When
        courseService.addCourse(addCourseDTO);

        // Then
        verify(courseMapper, times(1)).insert(any(Course.class));
    }

    /**
     * 测试新增课程 - 分类不存在
     */
    @Test(expected = BusinessException.class)
    public void testAddCourse_CategoryNotExists() {
        // Given
        AddCourseDTO addCourseDTO = new AddCourseDTO();
        addCourseDTO.setCourseName("新课程");
        addCourseDTO.setCategoryId(999L);

        when(categoryMapper.selectById(999L)).thenReturn(null);

        // When
        courseService.addCourse(addCourseDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改课程 - 成功场景
     */
    @Test
    public void testModifyCourse_Success() {
        // Given
        ModifyCourseDTO modifyCourseDTO = new ModifyCourseDTO();
        modifyCourseDTO.setId(1L);
        modifyCourseDTO.setCourseName("修改后的课程");

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        courseService.modifyCourse(modifyCourseDTO);

        // Then
        verify(courseMapper, times(1)).updateById(any(Course.class));
    }

    /**
     * 测试修改课程 - 课程不存在
     */
    @Test(expected = BusinessException.class)
    public void testModifyCourse_NotExists() {
        // Given
        ModifyCourseDTO modifyCourseDTO = new ModifyCourseDTO();
        modifyCourseDTO.setId(999L);
        modifyCourseDTO.setCourseName("修改后的课程");

        when(courseMapper.selectById(999L)).thenReturn(null);

        // When
        courseService.modifyCourse(modifyCourseDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改课程 - 修改分类时校验分类存在
     */
    @Test
    public void testModifyCourse_ChangeCategory() {
        // Given
        ModifyCourseDTO modifyCourseDTO = new ModifyCourseDTO();
        modifyCourseDTO.setId(1L);
        modifyCourseDTO.setCategoryId(2L);

        Category newCategory = new Category();
        newCategory.setId(2L);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(categoryMapper.selectById(2L)).thenReturn(newCategory);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        courseService.modifyCourse(modifyCourseDTO);

        // Then
        verify(courseMapper, times(1)).updateById(any(Course.class));
    }

    /**
     * 测试删除课程 - 成功场景
     */
    @Test
    public void testDelCourse_Success() {
        // Given
        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(chapterMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(courseMapper.deleteById(1L)).thenReturn(1);

        // When
        courseService.delCourse(1L);

        // Then
        verify(courseMapper, times(1)).deleteById(1L);
    }

    /**
     * 测试删除课程 - 课程已发布
     */
    @Test(expected = BusinessException.class)
    public void testDelCourse_AlreadyPublished() {
        // Given
        testCourse.setIsPublished(true);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);

        // When
        courseService.delCourse(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试删除课程 - 存在章节
     */
    @Test(expected = BusinessException.class)
    public void testDelCourse_HasChapters() {
        // Given
        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(chapterMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L);

        // When
        courseService.delCourse(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试发布课程 - 成功场景
     */
    @Test
    public void testPublishCourse_Success() {
        // Given
        testCourse.setChapterState(1); // 存在章节

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        courseService.publishCourse(1L);

        // Then
        verify(courseMapper, times(1)).updateById(any(Course.class));
    }

    /**
     * 测试发布课程 - 课程已发布
     */
    @Test(expected = BusinessException.class)
    public void testPublishCourse_AlreadyPublished() {
        // Given
        testCourse.setIsPublished(true);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);

        // When
        courseService.publishCourse(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试发布课程 - 课程无章节
     */
    @Test(expected = BusinessException.class)
    public void testPublishCourse_NoChapters() {
        // Given
        testCourse.setChapterState(0); // 无章节

        when(courseMapper.selectById(1L)).thenReturn(testCourse);

        // When
        courseService.publishCourse(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试下架课程 - 成功场景
     */
    @Test
    public void testUnpublishCourse_Success() {
        // Given
        testCourse.setIsPublished(true);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        courseService.unpublishCourse(1L);

        // Then
        verify(courseMapper, times(1)).updateById(any(Course.class));
    }

    /**
     * 测试下架课程 - 课程未发布
     */
    @Test(expected = BusinessException.class)
    public void testUnpublishCourse_NotPublished() {
        // Given
        testCourse.setIsPublished(false);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);

        // When
        courseService.unpublishCourse(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试根据ID查询课程 - 成功场景
     */
    @Test
    public void testGetCourseById_Success() {
        // Given
        when(courseMapper.selectById(1L)).thenReturn(testCourse);

        // When
        Course result = courseService.getCourseById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("测试课程", result.getCourseName());
    }

    /**
     * 测试根据ID查询课程 - 课程不存在
     */
    @Test(expected = BusinessException.class)
    public void testGetCourseById_NotExists() {
        // Given
        when(courseMapper.selectById(999L)).thenReturn(null);

        // When
        courseService.getCourseById(999L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试获取课程详情 - 成功场景（含章节和课时）
     */
    @Test
    public void testGetCourseDetail_Success() {
        // Given
        testCourse.setChapterState(1);

        List<Chapter> chapters = Arrays.asList(testChapter);
        List<Lesson> lessons = Arrays.asList(testLesson);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(chapterService.getChaptersByCourseId(1L)).thenReturn(chapters);
        when(lessonService.getLessonsByChapterIds(any())).thenReturn(lessons);

        // When
        CourseDetailVO result = courseService.getCourseDetail(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("测试课程", result.getCourseName());
        assertEquals(1, result.getChapters().size());
        assertEquals("第一章", result.getChapters().get(0).getChapterName());
        assertEquals(1, result.getChapters().get(0).getLessons().size());
        assertEquals("第一课时", result.getChapters().get(0).getLessons().get(0).getLessonName());
    }

    /**
     * 测试获取课程详情 - 无章节
     */
    @Test
    public void testGetCourseDetail_NoChapters() {
        // Given
        testCourse.setChapterState(0);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(chapterService.getChaptersByCourseId(1L)).thenReturn(new ArrayList<>());

        // When
        CourseDetailVO result = courseService.getCourseDetail(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("测试课程", result.getCourseName());
        assertTrue(result.getChapters().isEmpty());
    }
}
