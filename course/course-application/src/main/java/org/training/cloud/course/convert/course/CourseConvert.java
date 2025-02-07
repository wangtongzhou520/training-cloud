package org.training.cloud.course.convert.course;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.course.AddCourseDTO;
import org.training.cloud.course.dto.course.ModifyCourseDTO;
import org.training.cloud.course.entity.course.Course;
import org.training.cloud.course.vo.course.CourseVO;


@Mapper
public interface CourseConvert {


    CourseConvert INSTANCE = Mappers.getMapper(CourseConvert.class);


    Course convert(AddCourseDTO addInfoDTO);

    PageResponse<CourseVO> convert(PageResponse<Course> infoPageResponse);


    Course convert(ModifyCourseDTO modifyInfoDTO);


    Course convert(Course info);





}