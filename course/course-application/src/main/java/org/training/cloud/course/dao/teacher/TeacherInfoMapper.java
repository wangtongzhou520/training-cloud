package org.training.cloud.course.dao.teacher;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.course.dto.teacher.TeacherDTO;
import org.training.cloud.course.entity.teacher.TeacherInfo;


@Mapper
public interface TeacherInfoMapper extends BaseMapperExtend<TeacherInfo> {

    default PageResponse<TeacherInfo> selectPage(TeacherDTO teacherDTO) {
        return selectPage(teacherDTO, new LambdaQueryWrapperExtend<TeacherInfo>()
                .likeIfPresent(TeacherInfo::getRealName, teacherDTO.getRealName())
                .eqIfPresent(TeacherInfo::getStatus, teacherDTO.getStatus())
                .eqIfPresent(TeacherInfo::getIsCertified, teacherDTO.getIsCertified())
                .eqIfPresent(TeacherInfo::getDeleteState, teacherDTO.getDeleteState())
                .orderByDesc(TeacherInfo::getId)
        );
    }

}
