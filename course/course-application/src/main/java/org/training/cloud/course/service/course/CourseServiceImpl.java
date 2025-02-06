package org.training.cloud.course.service.course;


import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.convert.course.CourseConvert;
import org.training.cloud.course.dao.course.CourseMapper;
import org.training.cloud.course.dto.course.AddCourseDTO;
import org.training.cloud.course.dto.course.CourseDTO;
import org.training.cloud.course.dto.course.ModifyCourseDTO;
import org.training.cloud.course.entity.course.Course;

import javax.annotation.Resource;
import java.util.Objects;

import static org.training.cloud.course.constant.CourseExceptionEnumConstants.COURSE_NOT_EXISTS;


@Service
public class CourseServiceImpl implements CourseService {

    @Resource
    private CourseMapper courseMapper;

    @Override
    public void addCourse(AddCourseDTO addCourseDTO) {
        Course course = CourseConvert.INSTANCE.convert(addCourseDTO);
        courseMapper.insert(course);
    }


    @Override
    public void modifyCourse(ModifyCourseDTO modifyCourseDTO) {
        checkExistById(modifyCourseDTO.getId());
        Course course = CourseConvert.INSTANCE.convert(modifyCourseDTO);
        courseMapper.updateById(course);
    }


    @Override
    public PageResponse<Course> pageInfo(CourseDTO courseDTO) {
        return courseMapper.selectPage(courseDTO);
    }


    @Override
    public void delCourse(Long id) {
        checkExistById(id);
        courseMapper.deleteById(id);
    }


    @Override
    public Course getCourseById(Long id) {
        return courseMapper.selectById(id);
    }


    private void checkExistById(Long id) {
        Course info = courseMapper.selectById(id);
        if (Objects.isNull(info)) {
            throw new BusinessException(COURSE_NOT_EXISTS);
        }
    }
}