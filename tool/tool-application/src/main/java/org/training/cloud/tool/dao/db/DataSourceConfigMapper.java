package org.training.cloud.tool.dao.db;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.tool.dto.db.DataSourceConfigDTO;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.entity.db.ToolDataSourceConfig;

/**
 * 数据源配置
 *
 * @author wangtongzhou 18635604249
 * @since 2024-02-25 21:38
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperExtend<ToolDataSourceConfig> {



    default PageResponse<ToolDataSourceConfig> selectPage(DataSourceConfigDTO dataSourceConfigDTO) {
        return selectPage(dataSourceConfigDTO, new LambdaQueryWrapperExtend<ToolDataSourceConfig>()
                .likeIfPresent(ToolDataSourceConfig::getName,
                        dataSourceConfigDTO.getName()));
    }
}
