package org.training.cloud.course.convert.teacher;


import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.teacher.AddTeacherDTO;
import org.training.cloud.course.dto.teacher.ModifyTeacherDTO;
import org.training.cloud.course.entity.teacher.TeacherInfo;
import org.training.cloud.course.vo.teacher.TeacherDetailVO;
import org.training.cloud.course.vo.teacher.TeacherVO;

import java.util.List;


@Mapper
public interface TeacherConvert {

    TeacherConvert INSTANCE = Mappers.getMapper(TeacherConvert.class);

    TeacherInfo convert(AddTeacherDTO addTeacherDTO);

    TeacherInfo convert(ModifyTeacherDTO modifyTeacherDTO);

    @Named("toVO")
    TeacherVO convert(TeacherInfo teacherInfo);

    TeacherDetailVO convertToDetail(TeacherInfo teacherInfo);

    List<TeacherVO> convertList(List<TeacherInfo> teacherInfoList);

    PageResponse<TeacherVO> convert(PageResponse<TeacherInfo> teacherInfoPageResponse);

}
