package org.training.cloud.course.dao.material;


import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.course.dto.material.CourseMaterialDTO;
import org.training.cloud.course.entity.material.CourseMaterial;


@Mapper
public interface CourseMaterialMapper extends BaseMapperExtend<CourseMaterial> {

    default PageResponse<CourseMaterial> selectPage(CourseMaterialDTO materialDTO) {
        return selectPage(materialDTO, new LambdaQueryWrapperExtend<CourseMaterial>()
                .eqIfPresent(CourseMaterial::getCourseId, materialDTO.getCourseId())
                .eqIfPresent(CourseMaterial::getLessonId, materialDTO.getLessonId())
                .likeIfPresent(CourseMaterial::getMaterialName, materialDTO.getMaterialName())
                .eqIfPresent(CourseMaterial::getMaterialType, materialDTO.getMaterialType())
                .eqIfPresent(CourseMaterial::getIsFree, materialDTO.getIsFree())
                .eqIfPresent(CourseMaterial::getDeleteState, materialDTO.getDeleteState())
                .orderByAsc(CourseMaterial::getSort)
                .orderByDesc(CourseMaterial::getId)
        );
    }

}
