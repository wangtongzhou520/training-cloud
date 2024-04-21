package org.training.cloud.tool.dao.file;



import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.tool.dto.file.FileDTO;
import org.training.cloud.tool.entity.file.File;

/**
 * 文件管理
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:21
 */

@Mapper
public interface FileMapper extends BaseMapperExtend<File>{

    default PageResponse<File> selectPage(FileDTO fileDTO) {
        return selectPage(fileDTO,new LambdaQueryWrapperExtend<File>()
                .likeIfPresent(File::getName, fileDTO.getName())
                .eqIfPresent(File::getPath, fileDTO.getPath())
                .orderByDesc(File::getId)
        );
    }
}
