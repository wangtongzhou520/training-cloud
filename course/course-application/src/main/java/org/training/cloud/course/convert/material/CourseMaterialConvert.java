package org.training.cloud.course.convert.material;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.course.dto.material.AddCourseMaterialDTO;
import org.training.cloud.course.dto.material.ModifyCourseMaterialDTO;
import org.training.cloud.course.entity.material.CourseMaterial;
import org.training.cloud.course.vo.material.CourseMaterialVO;

import java.util.List;


@Mapper
public interface CourseMaterialConvert {

    CourseMaterialConvert INSTANCE = Mappers.getMapper(CourseMaterialConvert.class);

    CourseMaterial convert(AddCourseMaterialDTO addCourseMaterialDTO);

    CourseMaterial convert(ModifyCourseMaterialDTO modifyCourseMaterialDTO);

    CourseMaterialVO convert(CourseMaterial courseMaterial);

    List<CourseMaterialVO> convertList(List<CourseMaterial> courseMaterials);

    PageResponse<CourseMaterialVO> convert(PageResponse<CourseMaterial> courseMaterialPageResponse);

}
