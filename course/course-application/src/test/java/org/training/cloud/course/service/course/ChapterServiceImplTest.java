package org.training.cloud.course.service.course;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.dao.course.ChapterMapper;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dao.course.LessonMapper;
import org.training.cloud.course.dto.course.AddChapterDTO;
import org.training.cloud.course.dto.course.ModifyChapterDTO;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.entity.course.Course;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 章节服务单元测试
 *
 * @author wangtongzhou
 * @since 2025-01-29
 */
@RunWith(MockitoJUnitRunner.class)
public class ChapterServiceImplTest {

    @InjectMocks
    private ChapterServiceImpl chapterService;

    @Mock
    private ChapterMapper chapterMapper;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private LessonMapper lessonMapper;

    private Chapter testChapter;
    private Course testCourse;

    @Before
    public void setUp() {
        testCourse = new Course();
        testCourse.setId(1L);
        testCourse.setCourseName("测试课程");
        testCourse.setChapterState(0);

        testChapter = new Chapter();
        testChapter.setId(1L);
        testChapter.setChapterName("第一章");
        testChapter.setCourseId(1L);
        testChapter.setSort(1);
        testChapter.setDeleteState(false);
    }

    /**
     * 测试新增章节 - 成功场景（未指定排序）
     */
    @Test
    public void testAddChapter_Success_AutoSort() {
        // Given
        AddChapterDTO addChapterDTO = new AddChapterDTO();
        addChapterDTO.setChapterName("新章节");
        addChapterDTO.setCourseId(1L);
        // 未设置sort

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(chapterMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(Collections.emptyList());
        when(chapterMapper.insert(any(Chapter.class))).thenReturn(1);
        when(chapterMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        chapterService.addChapter(addChapterDTO);

        // Then
        verify(chapterMapper, times(1)).insert(any(Chapter.class));
        verify(courseMapper, times(1)).updateById(any(Course.class)); // 更新课程章节状态
    }

    /**
     * 测试新增章节 - 课程不存在
     */
    @Test(expected = BusinessException.class)
    public void testAddChapter_CourseNotExists() {
        // Given
        AddChapterDTO addChapterDTO = new AddChapterDTO();
        addChapterDTO.setChapterName("新章节");
        addChapterDTO.setCourseId(999L);

        when(courseMapper.selectById(999L)).thenReturn(null);

        // When
        chapterService.addChapter(addChapterDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试新增章节 - 自动设置排序为最大值+1
     */
    @Test
    public void testAddChapter_AutoSortIncrement() {
        // Given
        AddChapterDTO addChapterDTO = new AddChapterDTO();
        addChapterDTO.setChapterName("新章节");
        addChapterDTO.setCourseId(1L);

        Chapter existingChapter = new Chapter();
        existingChapter.setSort(5);

        when(courseMapper.selectById(1L)).thenReturn(testCourse);
        when(chapterMapper.selectList(any(LambdaQueryWrapperExtend.class)))
                .thenReturn(Collections.singletonList(existingChapter));
        when(chapterMapper.insert(any(Chapter.class))).thenReturn(1);
        when(chapterMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        chapterService.addChapter(addChapterDTO);

        // Then
        verify(chapterMapper, times(1)).insert(any(Chapter.class));
    }

    /**
     * 测试修改章节 - 成功场景
     */
    @Test
    public void testModifyChapter_Success() {
        // Given
        ModifyChapterDTO modifyChapterDTO = new ModifyChapterDTO();
        modifyChapterDTO.setId(1L);
        modifyChapterDTO.setChapterName("修改后的章节");

        when(chapterMapper.selectById(1L)).thenReturn(testChapter);
        when(chapterMapper.updateById(any(Chapter.class))).thenReturn(1);

        // When
        chapterService.modifyChapter(modifyChapterDTO);

        // Then
        verify(chapterMapper, times(1)).updateById(any(Chapter.class));
    }

    /**
     * 测试修改章节 - 章节不存在
     */
    @Test(expected = BusinessException.class)
    public void testModifyChapter_NotExists() {
        // Given
        ModifyChapterDTO modifyChapterDTO = new ModifyChapterDTO();
        modifyChapterDTO.setId(999L);
        modifyChapterDTO.setChapterName("修改后的章节");

        when(chapterMapper.selectById(999L)).thenReturn(null);

        // When
        chapterService.modifyChapter(modifyChapterDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改章节 - 修改课程ID时更新章节状态
     */
    @Test
    public void testModifyChapter_ChangeCourse() {
        // Given
        ModifyChapterDTO modifyChapterDTO = new ModifyChapterDTO();
        modifyChapterDTO.setId(1L);
        modifyChapterDTO.setCourseId(2L); // 修改课程ID

        Course newCourse = new Course();
        newCourse.setId(2L);

        when(chapterMapper.selectById(1L)).thenReturn(testChapter);
        when(courseMapper.selectById(2L)).thenReturn(newCourse);
        when(chapterMapper.updateById(any(Chapter.class))).thenReturn(1);
        when(chapterMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        chapterService.modifyChapter(modifyChapterDTO);

        // Then
        verify(chapterMapper, times(1)).updateById(any(Chapter.class));
        verify(courseMapper, times(2)).updateById(any(Course.class)); // 更新新旧课程的章节状态
    }

    /**
     * 测试删除章节 - 成功场景
     */
    @Test
    public void testDelChapter_Success() {
        // Given
        when(chapterMapper.selectById(1L)).thenReturn(testChapter);
        when(lessonMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(chapterMapper.deleteById(1L)).thenReturn(1);
        when(chapterMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(0L);
        when(courseMapper.updateById(any(Course.class))).thenReturn(1);

        // When
        chapterService.delChapter(1L);

        // Then
        verify(chapterMapper, times(1)).deleteById(1L);
        verify(courseMapper, times(1)).updateById(any(Course.class)); // 更新课程章节状态
    }

    /**
     * 测试删除章节 - 存在课时
     */
    @Test(expected = BusinessException.class)
    public void testDelChapter_HasLessons() {
        // Given
        when(chapterMapper.selectById(1L)).thenReturn(testChapter);
        when(lessonMapper.selectCount(any(LambdaQueryWrapperExtend.class))).thenReturn(1L); // 有课时

        // When
        chapterService.delChapter(1L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试根据ID查询章节 - 成功场景
     */
    @Test
    public void testGetChapterById_Success() {
        // Given
        when(chapterMapper.selectById(1L)).thenReturn(testChapter);

        // When
        Chapter result = chapterService.getChapterById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("第一章", result.getChapterName());
    }

    /**
     * 测试根据ID查询章节 - 章节不存在
     */
    @Test(expected = BusinessException.class)
    public void testGetChapterById_NotExists() {
        // Given
        when(chapterMapper.selectById(999L)).thenReturn(null);

        // When
        chapterService.getChapterById(999L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试根据课程ID查询章节列表
     */
    @Test
    public void testGetChaptersByCourseId() {
        // Given
        List<Chapter> chapters = new ArrayList<>();
        chapters.add(testChapter);

        when(chapterMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(chapters);

        // When
        List<Chapter> result = chapterService.getChaptersByCourseId(1L);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("第一章", result.get(0).getChapterName());
    }
}
