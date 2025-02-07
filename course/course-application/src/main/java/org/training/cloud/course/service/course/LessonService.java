package org.training.cloud.course.service.course;


import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.course.AddLessonDTO;
import org.training.cloud.course.dto.course.LessonDTO;
import org.training.cloud.course.dto.course.ModifyLessonDTO;
import org.training.cloud.course.entity.course.Lesson;


/**
 * 课程内容 Service 接口
 *
 * @author
 */
public interface LessonService {



    /**
     * 创建课程内容
     *
     * @param addLessonDTO
     */
    void addLesson(AddLessonDTO addLessonDTO);



    /**
     * 修改课程内容
     *
     * @param modifyLessonDTO
     */
    void modifyLesson(ModifyLessonDTO modifyLessonDTO);


    /**
     * 分页课程内容
     *
     * @param lessonDTO
     */
    PageResponse<Lesson> pageLesson(LessonDTO lessonDTO);


    /**
     * 删除课程内容
     *
     * @param id
     */
    void delLesson(Long id);




    /**
     * 查询单个课程内容
     *
     * @param id
     * @return
     */
    Lesson getLessonById(Long id);


}