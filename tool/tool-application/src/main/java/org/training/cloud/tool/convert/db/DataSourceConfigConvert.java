package org.training.cloud.tool.convert.db;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.dto.db.AddDataSourceConfigDTO;
import org.training.cloud.tool.dto.db.ModifyDataSourceConfigDTO;
import org.training.cloud.tool.entity.db.ToolDataSourceConfig;
import org.training.cloud.tool.vo.db.DataSourceConfigVO;
import org.training.cloud.tool.vo.db.DatabaseTableVO;

/**
 * DataSourceConfig Convert
 *
 * @author wangtongzhou
 * @since 2024-02-26 20:43
 */
@Mapper
public interface DataSourceConfigConvert {
    DataSourceConfigConvert INSTANCE = Mappers.getMapper(DataSourceConfigConvert.class);


    /**
     * addDataSourceConfigDTO convert DataSourceConfig
     *
     * @param addDataSourceConfigDTO
     * @return sysUserDO
     */
    @Mappings({})
    ToolDataSourceConfig convert(AddDataSourceConfigDTO addDataSourceConfigDTO);


    /**
     * modifyDataSourceConfigDTO convert DataSourceConfig
     *
     * @param modifyDataSourceConfigDTO
     * @return
     */
    @Mappings({})
    ToolDataSourceConfig convert(ModifyDataSourceConfigDTO modifyDataSourceConfigDTO);



    PageResponse<DataSourceConfigVO> convert(PageResponse<ToolDataSourceConfig> configPageResponse);


    DataSourceConfigVO convert(ToolDataSourceConfig toolDataSourceConfig);

}
