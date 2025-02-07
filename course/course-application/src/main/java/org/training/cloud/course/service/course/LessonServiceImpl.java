package org.training.cloud.course.service.course;


import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.course.LessonConvert;
import org.training.cloud.course.dao.course.LessonMapper;
import org.training.cloud.course.dto.course.AddLessonDTO;
import org.training.cloud.course.dto.course.LessonDTO;
import org.training.cloud.course.dto.course.ModifyLessonDTO;
import org.training.cloud.course.entity.course.Lesson;

import javax.annotation.Resource;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.LESSON_NOT_EXISTS;


@Service
public class LessonServiceImpl implements LessonService {

    @Resource
    private LessonMapper lessonMapper;

    @Override
    public void addLesson(AddLessonDTO addLessonDTO) {
        Lesson lesson = LessonConvert.INSTANCE.convert(addLessonDTO);
        lessonMapper.insert(lesson);
    }


    @Override
    public void modifyLesson(ModifyLessonDTO modifyLessonDTO) {
        checkExistById(modifyLessonDTO.getId());
        Lesson lesson = LessonConvert.INSTANCE.convert(modifyLessonDTO);
        lessonMapper.updateById(lesson);
    }


    @Override
    public PageResponse<Lesson> pageLesson(LessonDTO lessonDTO) {
        return lessonMapper.selectPage(lessonDTO);
    }


    @Override
    public void delLesson(Long id) {
        checkExistById(id);
        lessonMapper.deleteById(id);
    }


    @Override
    public Lesson getLessonById(Long id) {
        return lessonMapper.selectById(id);
    }


    private void checkExistById(Long id) {
        Lesson lesson = lessonMapper.selectById(id);
        if (Objects.isNull(lesson)) {
            throw new BusinessException(LESSON_NOT_EXISTS);
        }
    }
}