package org.training.cloud.tool.service.db;

import org.springframework.stereotype.Service;
import org.training.cloud.tool.dto.db.AddDataSourceConfigDTO;
import org.training.cloud.tool.dto.db.ModifyDataSourceConfigDTO;
import org.training.cloud.tool.entity.db.DataSourceConfig;

import java.util.List;

/**
 * 数据源配置
 *
 * @author wangtongzhou
 * @since 2024-02-25 21:41
 */
@Service
public class DataSourceConfigServiceImpl implements DataSourceConfigService {
    @Override
    public Long createDataSourceConfig(AddDataSourceConfigDTO addDataSourceConfigDTO) {
        return null;
    }

    @Override
    public void updateDataSourceConfig(ModifyDataSourceConfigDTO modifyDataSourceConfigDTO) {

    }

    @Override
    public void deleteDataSourceConfig(Long id) {

    }

    @Override
    public DataSourceConfig getDataSourceConfig(Long id) {
        return null;
    }

    @Override
    public List<DataSourceConfig> getDataSourceConfigList() {
        return null;
    }
}
