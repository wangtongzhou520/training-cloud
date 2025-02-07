package org.training.cloud.course.dao.course;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.course.dto.course.LessonDTO;
import org.training.cloud.course.entity.course.Lesson;




@Mapper
public interface LessonMapper extends BaseMapperExtend<Lesson> {

    default PageResponse<Lesson> selectPage(LessonDTO lessonDTO) {
        return selectPage(lessonDTO,new LambdaQueryWrapperExtend<Lesson>()
                .likeIfPresent(Lesson::getLessonName, lessonDTO.getLessonName())
                .eqIfPresent(Lesson::getChapterId, lessonDTO.getChapterId())
                .eqIfPresent(Lesson::getSort, lessonDTO.getSort())
                .eqIfPresent(Lesson::getLessonUrl, lessonDTO.getLessonUrl())
                .eqIfPresent(Lesson::getDeleteState, lessonDTO.getDeleteState())
                .orderByDesc(Lesson::getId)
        );
    }

}