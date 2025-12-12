package org.training.cloud.course.convert.teacher;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.course.dto.teacher.BindTeacherDTO;
import org.training.cloud.course.entity.teacher.CourseTeacherRelation;
import org.training.cloud.course.vo.teacher.CourseTeacherVO;

import java.util.List;


@Mapper
public interface CourseTeacherRelationConvert {

    CourseTeacherRelationConvert INSTANCE = Mappers.getMapper(CourseTeacherRelationConvert.class);

    CourseTeacherRelation convert(BindTeacherDTO bindTeacherDTO);

    CourseTeacherVO convert(CourseTeacherRelation courseTeacherRelation);

    List<CourseTeacherVO> convert(List<CourseTeacherRelation> courseTeacherRelations);

}
