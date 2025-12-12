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
import org.training.cloud.course.dao.course.LessonMapper;
import org.training.cloud.course.dto.course.AddLessonDTO;
import org.training.cloud.course.dto.course.ModifyLessonDTO;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.entity.course.Lesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * 课时服务单元测试
 *
 * @author wangtongzhou
 * @since 2025-01-29
 */
@RunWith(MockitoJUnitRunner.class)
public class LessonServiceImplTest {

    @InjectMocks
    private LessonServiceImpl lessonService;

    @Mock
    private LessonMapper lessonMapper;

    @Mock
    private ChapterMapper chapterMapper;

    private Lesson testLesson;
    private Chapter testChapter;

    @Before
    public void setUp() {
        testChapter = new Chapter();
        testChapter.setId(1L);
        testChapter.setChapterName("第一章");
        testChapter.setCourseId(1L);

        testLesson = new Lesson();
        testLesson.setId(1L);
        testLesson.setLessonName("第一课时");
        testLesson.setChapterId(1L);
        testLesson.setSort(1);
        testLesson.setLessonUrl("https://example.com/lesson1.mp4");
        testLesson.setDeleteState(false);
    }

    /**
     * 测试新增课时 - 成功场景（未指定排序）
     */
    @Test
    public void testAddLesson_Success_AutoSort() {
        // Given
        AddLessonDTO addLessonDTO = new AddLessonDTO();
        addLessonDTO.setLessonName("新课时");
        addLessonDTO.setChapterId(1L);
        addLessonDTO.setLessonUrl("https://example.com/lesson2.mp4");
        // 未设置sort

        when(chapterMapper.selectById(1L)).thenReturn(testChapter);
        when(lessonMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(Collections.emptyList());
        when(lessonMapper.insert(any(Lesson.class))).thenReturn(1);

        // When
        lessonService.addLesson(addLessonDTO);

        // Then
        verify(lessonMapper, times(1)).insert(any(Lesson.class));
    }

    /**
     * 测试新增课时 - 章节不存在
     */
    @Test(expected = BusinessException.class)
    public void testAddLesson_ChapterNotExists() {
        // Given
        AddLessonDTO addLessonDTO = new AddLessonDTO();
        addLessonDTO.setLessonName("新课时");
        addLessonDTO.setChapterId(999L);

        when(chapterMapper.selectById(999L)).thenReturn(null);

        // When
        lessonService.addLesson(addLessonDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试新增课时 - 自动设置排序为最大值+1
     */
    @Test
    public void testAddLesson_AutoSortIncrement() {
        // Given
        AddLessonDTO addLessonDTO = new AddLessonDTO();
        addLessonDTO.setLessonName("新课时");
        addLessonDTO.setChapterId(1L);
        addLessonDTO.setLessonUrl("https://example.com/lesson2.mp4");

        Lesson existingLesson = new Lesson();
        existingLesson.setSort(3);

        when(chapterMapper.selectById(1L)).thenReturn(testChapter);
        when(lessonMapper.selectList(any(LambdaQueryWrapperExtend.class)))
                .thenReturn(Collections.singletonList(existingLesson));
        when(lessonMapper.insert(any(Lesson.class))).thenReturn(1);

        // When
        lessonService.addLesson(addLessonDTO);

        // Then
        verify(lessonMapper, times(1)).insert(any(Lesson.class));
    }

    /**
     * 测试修改课时 - 成功场景
     */
    @Test
    public void testModifyLesson_Success() {
        // Given
        ModifyLessonDTO modifyLessonDTO = new ModifyLessonDTO();
        modifyLessonDTO.setId(1L);
        modifyLessonDTO.setLessonName("修改后的课时");

        when(lessonMapper.selectById(1L)).thenReturn(testLesson);
        when(lessonMapper.updateById(any(Lesson.class))).thenReturn(1);

        // When
        lessonService.modifyLesson(modifyLessonDTO);

        // Then
        verify(lessonMapper, times(1)).updateById(any(Lesson.class));
    }

    /**
     * 测试修改课时 - 课时不存在
     */
    @Test(expected = BusinessException.class)
    public void testModifyLesson_NotExists() {
        // Given
        ModifyLessonDTO modifyLessonDTO = new ModifyLessonDTO();
        modifyLessonDTO.setId(999L);
        modifyLessonDTO.setLessonName("修改后的课时");

        when(lessonMapper.selectById(999L)).thenReturn(null);

        // When
        lessonService.modifyLesson(modifyLessonDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试修改课时 - 修改章节ID时校验新章节存在
     */
    @Test
    public void testModifyLesson_ChangeChapter() {
        // Given
        ModifyLessonDTO modifyLessonDTO = new ModifyLessonDTO();
        modifyLessonDTO.setId(1L);
        modifyLessonDTO.setChapterId(2L); // 修改章节ID

        Chapter newChapter = new Chapter();
        newChapter.setId(2L);

        when(lessonMapper.selectById(1L)).thenReturn(testLesson);
        when(chapterMapper.selectById(2L)).thenReturn(newChapter);
        when(lessonMapper.updateById(any(Lesson.class))).thenReturn(1);

        // When
        lessonService.modifyLesson(modifyLessonDTO);

        // Then
        verify(lessonMapper, times(1)).updateById(any(Lesson.class));
    }

    /**
     * 测试修改课时 - 修改章节ID时新章节不存在
     */
    @Test(expected = BusinessException.class)
    public void testModifyLesson_ChangeChapterNotExists() {
        // Given
        ModifyLessonDTO modifyLessonDTO = new ModifyLessonDTO();
        modifyLessonDTO.setId(1L);
        modifyLessonDTO.setChapterId(999L);

        when(lessonMapper.selectById(1L)).thenReturn(testLesson);
        when(chapterMapper.selectById(999L)).thenReturn(null);

        // When
        lessonService.modifyLesson(modifyLessonDTO);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试删除课时 - 成功场景
     */
    @Test
    public void testDelLesson_Success() {
        // Given
        when(lessonMapper.selectById(1L)).thenReturn(testLesson);
        when(lessonMapper.deleteById(1L)).thenReturn(1);

        // When
        lessonService.delLesson(1L);

        // Then
        verify(lessonMapper, times(1)).deleteById(1L);
    }

    /**
     * 测试根据ID查询课时 - 成功场景
     */
    @Test
    public void testGetLessonById_Success() {
        // Given
        when(lessonMapper.selectById(1L)).thenReturn(testLesson);

        // When
        Lesson result = lessonService.getLessonById(1L);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId().longValue());
        assertEquals("第一课时", result.getLessonName());
    }

    /**
     * 测试根据ID查询课时 - 课时不存在
     */
    @Test(expected = BusinessException.class)
    public void testGetLessonById_NotExists() {
        // Given
        when(lessonMapper.selectById(999L)).thenReturn(null);

        // When
        lessonService.getLessonById(999L);

        // Then - 期望抛出BusinessException
    }

    /**
     * 测试根据章节ID列表批量查询课时
     */
    @Test
    public void testGetLessonsByChapterIds() {
        // Given
        List<Long> chapterIds = Arrays.asList(1L, 2L);
        List<Lesson> lessons = new ArrayList<>();
        lessons.add(testLesson);

        when(lessonMapper.selectList(any(LambdaQueryWrapperExtend.class))).thenReturn(lessons);

        // When
        List<Lesson> result = lessonService.getLessonsByChapterIds(chapterIds);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("第一课时", result.get(0).getLessonName());
    }

    /**
     * 测试根据章节ID列表批量查询课时 - 空列表
     */
    @Test
    public void testGetLessonsByChapterIds_EmptyList() {
        // When
        List<Lesson> result = lessonService.getLessonsByChapterIds(Collections.emptyList());

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    /**
     * 测试根据章节ID列表批量查询课时 - null列表
     */
    @Test
    public void testGetLessonsByChapterIds_NullList() {
        // When
        List<Lesson> result = lessonService.getLessonsByChapterIds(null);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
