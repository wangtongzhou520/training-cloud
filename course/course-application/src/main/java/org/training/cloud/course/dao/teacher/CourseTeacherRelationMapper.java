package org.training.cloud.course.dao.teacher;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.course.entity.teacher.CourseTeacherRelation;


@Mapper
public interface CourseTeacherRelationMapper extends BaseMapperExtend<CourseTeacherRelation> {

}
