package org.training.cloud.course.dao.course;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.course.dto.course.CourseDTO;
import org.training.cloud.course.entity.course.Course;




@Mapper
public interface CourseMapper extends BaseMapperExtend<Course> {

    default PageResponse<Course> selectPage(CourseDTO infoDTO) {
        return selectPage(infoDTO,new LambdaQueryWrapperExtend<Course>()
                .likeIfPresent(Course::getCourseName, infoDTO.getCourseName())
                .eqIfPresent(Course::getDescription, infoDTO.getDescription())
                .eqIfPresent(Course::getCategoryId, infoDTO.getCategoryId())
                .eqIfPresent(Course::getThumbnailUrl, infoDTO.getThumbnailUrl())
                .eqIfPresent(Course::getIsPublished, infoDTO.getIsPublished())
                .eqIfPresent(Course::getDeleteState, infoDTO.getDeleteState())
                .orderByDesc(Course::getId)
        );
    }

}