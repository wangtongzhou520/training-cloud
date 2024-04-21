package org.training.cloud.tool.convert.file;


import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.dto.file.AddFileDTO;
import org.training.cloud.tool.entity.file.File;
import org.training.cloud.tool.vo.file.FileVO;

/**
 * 文件转换
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:31
 */
@Mapper
public interface FileConvert {
    FileConvert INSTANCE = Mappers.getMapper(FileConvert.class);


    File convert(AddFileDTO addFileDTO);

    PageResponse<FileVO> convert(PageResponse<File> filePageResponse);


    FileVO convert(File file);
}
