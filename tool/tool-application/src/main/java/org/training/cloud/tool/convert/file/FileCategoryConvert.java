package org.training.cloud.tool.convert.file;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.dto.file.AddFileCategoryDTO;
import org.training.cloud.tool.dto.file.ModifyFileCategoryDTO;
import org.training.cloud.tool.entity.file.FileCategory;
import org.training.cloud.tool.vo.file.FileCategoryVO;

import java.util.List;


@Mapper
public interface FileCategoryConvert {


    FileCategoryConvert INSTANCE = Mappers.getMapper(FileCategoryConvert.class);


    FileCategory convert(AddFileCategoryDTO addFileCategoryDTO);

    PageResponse<FileCategoryVO> convert(PageResponse<FileCategory> fileCategoryPageResponse);


    FileCategory convert(ModifyFileCategoryDTO modifyFileCategoryDTO);


    FileCategoryVO convert(FileCategory fileCategory);



    List<FileCategoryVO> convert(List<FileCategory> fileCategories);






}