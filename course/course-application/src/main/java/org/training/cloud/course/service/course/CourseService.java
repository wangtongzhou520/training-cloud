package org.training.cloud.course.service.course;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.course.AddCourseDTO;
import org.training.cloud.course.dto.course.CourseDTO;
import org.training.cloud.course.dto.course.ModifyCourseDTO;
import org.training.cloud.course.entity.course.Course;


/**
 * 课程 Service 接口
 *
 * @author
 */
public interface CourseService {



    /**
     * 创建课程
     *
     * @param addCourseDTO
     */
    void addCourse(AddCourseDTO addCourseDTO);



    /**
     * 修改课程
     *
     * @param modifyCourseDTO
     */
    void modifyCourse(ModifyCourseDTO modifyCourseDTO);


    /**
     * 分页课程
     *
     * @param courseDTO
     */
    PageResponse<Course> pageInfo(CourseDTO courseDTO);


    /**
     * 删除课程
     *
     * @param id
     */
    void delCourse(Long id);




    /**
     * 查询单个课程
     *
     * @param id
     * @return
     */
    Course getCourseById(Long id);


}