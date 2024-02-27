package org.training.cloud.tool.convert.db;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.system.dto.user.AddUserDTO;
import org.training.cloud.system.dto.user.ModifyUserDTO;
import org.training.cloud.tool.dto.db.AddDataSourceConfigDTO;
import org.training.cloud.tool.dto.db.ModifyDataSourceConfigDTO;
import org.training.cloud.tool.entity.db.DataSourceConfig;

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
    DataSourceConfig convert(AddDataSourceConfigDTO addDataSourceConfigDTO);


    /**
     * modifyDataSourceConfigDTO convert DataSourceConfig
     *
     * @param modifyDataSourceConfigDTO
     * @return
     */
    @Mappings({})
    DataSourceConfig convert(ModifyDataSourceConfigDTO modifyDataSourceConfigDTO);
}
