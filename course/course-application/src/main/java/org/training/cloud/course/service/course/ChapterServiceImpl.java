package org.training.cloud.course.service.course;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.convert.course.ChapterConvert;
import org.training.cloud.course.dao.course.ChapterMapper;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dao.course.LessonMapper;
import org.training.cloud.course.dto.course.AddChapterDTO;
import org.training.cloud.course.dto.course.ChapterDTO;
import org.training.cloud.course.dto.course.ModifyChapterDTO;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.entity.course.Lesson;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;


@Service
public class ChapterServiceImpl implements ChapterService {

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private LessonMapper lessonMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addChapter(AddChapterDTO addChapterDTO) {
        // 校验课程是否存在
        checkCourseExists(addChapterDTO.getCourseId());

        // 转换章节信息
        Chapter chapter = ChapterConvert.INSTANCE.convert(addChapterDTO);

        // 处理排序：如果未指定sort，自动设置为最大sort+1
        if (chapter.getSort() == null) {
            Integer maxSort = chapterMapper.selectList(
                    new LambdaQueryWrapperExtend<Chapter>()
                            .eq(Chapter::getCourseId, addChapterDTO.getCourseId())
                            .eq(Chapter::getDeleteState, false)
                            .orderByDesc(Chapter::getSort)
                            .last("LIMIT 1")
            ).stream()
                    .map(Chapter::getSort)
                    .findFirst()
                    .orElse(0);
            chapter.setSort(maxSort + 1);
        }

        chapterMapper.insert(chapter);

        // 更新课程的章节状态为1（存在章节）
        updateCourseChapterState(addChapterDTO.getCourseId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyChapter(ModifyChapterDTO modifyChapterDTO) {
        Chapter existChapter = getChapterById(modifyChapterDTO.getId());

        // 如果修改了课程ID，需要校验新课程是否存在
        if (modifyChapterDTO.getCourseId() != null &&
                !modifyChapterDTO.getCourseId().equals(existChapter.getCourseId())) {
            checkCourseExists(modifyChapterDTO.getCourseId());

            // 更新新旧课程的章节状态
            Long oldCourseId = existChapter.getCourseId();
            updateCourseChapterState(modifyChapterDTO.getCourseId());
            updateCourseChapterState(oldCourseId);
        }

        Chapter chapter = ChapterConvert.INSTANCE.convert(modifyChapterDTO);
        chapterMapper.updateById(chapter);
    }


    @Override
    public PageResponse<Chapter> pageChapter(ChapterDTO chapterDTO) {
        return chapterMapper.selectPage(chapterDTO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delChapter(Long id) {
        Chapter chapter = getChapterById(id);

        // 检查章节下是否存在课时
        Long lessonCount = lessonMapper.selectCount(
                new LambdaQueryWrapperExtend<Lesson>()
                        .eq(Lesson::getChapterId, id)
                        .eq(Lesson::getDeleteState, false)
        );
        if (lessonCount > 0) {
            throw new BusinessException(CHAPTER_HAS_LESSONS);
        }

        chapterMapper.deleteById(id);

        // 删除章节后，更新课程的章节状态
        updateCourseChapterState(chapter.getCourseId());
    }


    @Override
    public Chapter getChapterById(Long id) {
        Chapter chapter = chapterMapper.selectById(id);
        if (Objects.isNull(chapter)) {
            throw new BusinessException(CHAPTER_NOT_EXISTS);
        }
        return chapter;
    }

    @Override
    public List<Chapter> getChaptersByCourseId(Long courseId) {
        return chapterMapper.selectList(
                new LambdaQueryWrapperExtend<Chapter>()
                        .eq(Chapter::getCourseId, courseId)
                        .eq(Chapter::getDeleteState, false)
                        .orderByAsc(Chapter::getSort)
        );
    }


    /**
     * 校验章节是否存在
     *
     * @param id 章节ID
     */
    private void checkExistById(Long id) {
        Chapter chapter = chapterMapper.selectById(id);
        if (Objects.isNull(chapter)) {
            throw new BusinessException(CHAPTER_NOT_EXISTS);
        }
    }

    /**
     * 校验课程是否存在
     *
     * @param courseId 课程ID
     */
    private void checkCourseExists(Long courseId) {
        Course course = courseMapper.selectById(courseId);
        if (Objects.isNull(course)) {
            throw new BusinessException(CHAPTER_COURSE_NOT_EXISTS);
        }
    }

    /**
     * 更新课程的章节状态
     * 如果课程下有章节，设置为1；如果没有章节，设置为0
     *
     * @param courseId 课程ID
     */
    private void updateCourseChapterState(Long courseId) {
        Long chapterCount = chapterMapper.selectCount(
                new LambdaQueryWrapperExtend<Chapter>()
                        .eq(Chapter::getCourseId, courseId)
                        .eq(Chapter::getDeleteState, false)
        );

        Course course = new Course();
        course.setId(courseId);
        course.setChapterState(chapterCount > 0 ? 1 : 0);
        courseMapper.updateById(course);
    }
}