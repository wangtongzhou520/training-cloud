package org.training.cloud.course.service.course;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.course.convert.course.CourseConvert;
import org.training.cloud.course.dao.category.CategoryMapper;
import org.training.cloud.course.dao.course.ChapterMapper;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dto.course.AddCourseDTO;
import org.training.cloud.course.dto.course.CourseDTO;
import org.training.cloud.course.dto.course.ModifyCourseDTO;
import org.training.cloud.course.entity.category.Category;
import org.training.cloud.course.entity.course.Chapter;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.entity.course.Lesson;
import org.training.cloud.course.vo.course.ChapterDetailVO;
import org.training.cloud.course.vo.course.CourseDetailVO;
import org.training.cloud.course.vo.course.LessonVO;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.*;


@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ChapterMapper chapterMapper;

    @Resource
    private ChapterService chapterService;

    @Resource
    private LessonService lessonService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCourse(AddCourseDTO addCourseDTO) {
        // 校验分类是否存在
        checkCategoryExists(addCourseDTO.getCategoryId());

        // 转换并初始化课程信息
        Course course = CourseConvert.INSTANCE.convert(addCourseDTO);
        course.setIsPublished(false); // 默认未发布
        course.setChapterState(0); // 默认无章节

        courseMapper.insert(course);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyCourse(ModifyCourseDTO modifyCourseDTO) {
        checkExistById(modifyCourseDTO.getId());

        // 如果修改分类，需要校验分类是否存在
        if (modifyCourseDTO.getCategoryId() != null) {
            checkCategoryExists(modifyCourseDTO.getCategoryId());
        }

        Course course = CourseConvert.INSTANCE.convert(modifyCourseDTO);
        courseMapper.updateById(course);
    }


    @Override
    public PageResponse<Course> pageInfo(CourseDTO courseDTO) {
        return courseMapper.selectPage(courseDTO);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delCourse(Long id) {
        Course course = getCourseById(id);

        // 检查课程是否已发布，已发布的课程不能删除
        if (Boolean.TRUE.equals(course.getIsPublished())) {
            throw new BusinessException(COURSE_ALREADY_PUBLISHED);
        }

        // 检查课程下是否存在章节
        Long chapterCount = chapterMapper.selectCount(
                new LambdaQueryWrapperExtend<Chapter>()
                        .eq(Chapter::getCourseId, id)
                        .eq(Chapter::getDeleteState, false)
        );
        if (chapterCount > 0) {
            throw new BusinessException(COURSE_HAS_CHAPTERS);
        }

        courseMapper.deleteById(id);
    }


    @Override
    public Course getCourseById(Long id) {
        Course course = courseMapper.selectById(id);
        if (Objects.isNull(course)) {
            throw new BusinessException(COURSE_NOT_EXISTS);
        }
        return course;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishCourse(Long id) {
        Course course = getCourseById(id);

        // 检查课程是否已发布
        if (Boolean.TRUE.equals(course.getIsPublished())) {
            throw new BusinessException(COURSE_ALREADY_PUBLISHED);
        }

        // 检查课程是否有章节，没有章节不能发布
        if (course.getChapterState() == null || course.getChapterState() == 0) {
            throw new BusinessException(new org.training.cloud.common.core.constant.ExceptionCode(
                    103004006, "课程暂无章节，无法发布"));
        }

        // 更新发布状态
        Course updateCourse = new Course();
        updateCourse.setId(id);
        updateCourse.setIsPublished(true);
        courseMapper.updateById(updateCourse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unpublishCourse(Long id) {
        Course course = getCourseById(id);

        // 检查课程是否已下架
        if (Boolean.FALSE.equals(course.getIsPublished())) {
            throw new BusinessException(COURSE_NOT_PUBLISHED);
        }

        // 更新发布状态
        Course updateCourse = new Course();
        updateCourse.setId(id);
        updateCourse.setIsPublished(false);
        courseMapper.updateById(updateCourse);
    }

    @Override
    public CourseDetailVO getCourseDetail(Long id) {
        // 1. 查询课程基本信息
        Course course = getCourseById(id);

        // 2. 转换为CourseDetailVO
        CourseDetailVO courseDetailVO = new CourseDetailVO();
        courseDetailVO.setId(course.getId())
                .setCourseName(course.getCourseName())
                .setCourseDescription(course.getCourseDescription())
                .setCategoryId(course.getCategoryId())
                .setThumbnailUrl(course.getThumbnailUrl())
                .setIsPublished(course.getIsPublished())
                .setChapterState(course.getChapterState())
                .setDeleteState(course.getDeleteState());

        // 3. 查询章节列表
        List<Chapter> chapters = chapterService.getChaptersByCourseId(id);
        if (chapters == null || chapters.isEmpty()) {
            courseDetailVO.setChapters(new ArrayList<>());
            return courseDetailVO;
        }

        // 4. 查询所有章节的课时
        List<Long> chapterIds = chapters.stream()
                .map(Chapter::getId)
                .collect(Collectors.toList());
        List<Lesson> lessons = lessonService.getLessonsByChapterIds(chapterIds);

        // 5. 按章节ID分组课时
        Map<Long, List<Lesson>> lessonMap = lessons.stream()
                .collect(Collectors.groupingBy(Lesson::getChapterId));

        // 6. 组装章节详情（包含课时）
        List<ChapterDetailVO> chapterDetailVOs = chapters.stream()
                .map(chapter -> {
                    ChapterDetailVO chapterDetailVO = new ChapterDetailVO();
                    chapterDetailVO.setId(chapter.getId())
                            .setChapterName(chapter.getChapterName())
                            .setCourseId(chapter.getCourseId())
                            .setSort(chapter.getSort())
                            .setDeleteState(chapter.getDeleteState());

                    // 设置该章节的课时列表
                    List<Lesson> chapterLessons = lessonMap.getOrDefault(chapter.getId(), new ArrayList<>());
                    List<LessonVO> lessonVOs = chapterLessons.stream()
                            .map(lesson -> {
                                LessonVO lessonVO = new LessonVO();
                                lessonVO.setId(lesson.getId())
                                        .setLessonName(lesson.getLessonName())
                                        .setChapterId(lesson.getChapterId())
                                        .setSort(lesson.getSort())
                                        .setLessonUrl(lesson.getLessonUrl())
                                        .setDeleteState(lesson.getDeleteState());
                                return lessonVO;
                            })
                            .collect(Collectors.toList());
                    chapterDetailVO.setLessons(lessonVOs);

                    return chapterDetailVO;
                })
                .collect(Collectors.toList());

        courseDetailVO.setChapters(chapterDetailVOs);
        return courseDetailVO;
    }


    /**
     * 校验课程是否存在
     *
     * @param id 课程ID
     */
    private void checkExistById(Long id) {
        Course course = courseMapper.selectById(id);
        if (Objects.isNull(course)) {
            throw new BusinessException(COURSE_NOT_EXISTS);
        }
    }

    /**
     * 校验分类是否存在
     *
     * @param categoryId 分类ID
     */
    private void checkCategoryExists(Long categoryId) {
        if (categoryId == null) {
            return;
        }
        Category category = categoryMapper.selectById(categoryId);
        if (Objects.isNull(category)) {
            throw new BusinessException(COURSE_CATEGORY_NOT_EXISTS);
        }
    }
}