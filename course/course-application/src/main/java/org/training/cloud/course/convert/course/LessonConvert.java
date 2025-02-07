package org.training.cloud.course.convert.course;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.course.AddLessonDTO;
import org.training.cloud.course.dto.course.ModifyLessonDTO;
import org.training.cloud.course.entity.course.Lesson;
import org.training.cloud.course.vo.course.LessonVO;


@Mapper
public interface LessonConvert {


    LessonConvert INSTANCE = Mappers.getMapper(LessonConvert.class);


    Lesson convert(AddLessonDTO addLessonDTO);

    PageResponse<LessonVO> convert(PageResponse<Lesson> lessonPageResponse);


    Lesson convert(ModifyLessonDTO modifyLessonDTO);


    LessonVO convert(Lesson lesson);





}