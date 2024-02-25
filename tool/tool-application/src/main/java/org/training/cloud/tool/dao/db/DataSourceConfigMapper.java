package org.training.cloud.tool.dao.db;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.tool.entity.db.DataSourceConfig;

/**
 * 数据源配置
 *
 * @author wangtongzhou 18635604249
 * @since 2024-02-25 21:38
 */
@Mapper
public interface DataSourceConfigMapper extends BaseMapperExtend<DataSourceConfig> {
}
