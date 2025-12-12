package org.training.cloud.course.service.course;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.convert.course.LessonConvert;
import org.training.cloud.course.dao.course.ChapterMapper;
import org.training.cloud.course.dao.course.LessonMapper;
import org.training.cloud.course.dto.course.AddLessonDTO;
import org.training.cloud.course.dto.course.LessonDTO;
import org.training.cloud.course.dto.course.ModifyLessonDTO;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.entity.course.Lesson;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;


@Service
public class LessonServiceImpl implements LessonService {

    @Resource
    private LessonMapper lessonMapper;

    @Resource
    private ChapterMapper chapterMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLesson(AddLessonDTO addLessonDTO) {
        // 校验章节是否存在
        checkChapterExists(addLessonDTO.getChapterId());

        // 转换课时信息
        Lesson lesson = LessonConvert.INSTANCE.convert(addLessonDTO);

        // 处理排序：如果未指定sort，自动设置为最大sort+1
        if (lesson.getSort() == null) {
            Integer maxSort = lessonMapper.selectList(
                    new LambdaQueryWrapperExtend<Lesson>()
                            .eq(Lesson::getChapterId, addLessonDTO.getChapterId())
                            .eq(Lesson::getDeleteState, false)
                            .orderByDesc(Lesson::getSort)
                            .last("LIMIT 1")
            ).stream()
                    .map(Lesson::getSort)
                    .findFirst()
                    .orElse(0);
            lesson.setSort(maxSort + 1);
        }

        lessonMapper.insert(lesson);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyLesson(ModifyLessonDTO modifyLessonDTO) {
        Lesson existLesson = getLessonById(modifyLessonDTO.getId());

        // 如果修改了章节ID，需要校验新章节是否存在
        if (modifyLessonDTO.getChapterId() != null &&
                !modifyLessonDTO.getChapterId().equals(existLesson.getChapterId())) {
            checkChapterExists(modifyLessonDTO.getChapterId());
        }

        Lesson lesson = LessonConvert.INSTANCE.convert(modifyLessonDTO);
        lessonMapper.updateById(lesson);
    }


    @Override
    public PageResponse<Lesson> pageLesson(LessonDTO lessonDTO) {
        return lessonMapper.selectPage(lessonDTO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delLesson(Long id) {
        checkExistById(id);
        lessonMapper.deleteById(id);
    }


    @Override
    public Lesson getLessonById(Long id) {
        Lesson lesson = lessonMapper.selectById(id);
        if (Objects.isNull(lesson)) {
            throw new BusinessException(LESSON_NOT_EXISTS);
        }
        return lesson;
    }

    @Override
    public List<Lesson> getLessonsByChapterIds(Collection<Long> chapterIds) {
        if (chapterIds == null || chapterIds.isEmpty()) {
            return Collections.emptyList();
        }
        return lessonMapper.selectList(
                new LambdaQueryWrapperExtend<Lesson>()
                        .in(Lesson::getChapterId, chapterIds)
                        .eq(Lesson::getDeleteState, false)
                        .orderByAsc(Lesson::getChapterId, Lesson::getSort)
        );
    }


    /**
     * 校验课时是否存在
     *
     * @param id 课时ID
     */
    private void checkExistById(Long id) {
        Lesson lesson = lessonMapper.selectById(id);
        if (Objects.isNull(lesson)) {
            throw new BusinessException(LESSON_NOT_EXISTS);
        }
    }

    /**
     * 校验章节是否存在
     *
     * @param chapterId 章节ID
     */
    private void checkChapterExists(Long chapterId) {
        Chapter chapter = chapterMapper.selectById(chapterId);
        if (Objects.isNull(chapter)) {
            throw new BusinessException(LESSON_CHAPTER_NOT_EXISTS);
        }
    }
}