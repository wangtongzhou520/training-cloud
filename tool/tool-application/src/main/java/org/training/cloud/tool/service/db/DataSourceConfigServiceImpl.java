package org.training.cloud.tool.service.db;

import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.mybatis.util.JdbcUtils;
import org.training.cloud.tool.convert.db.DataSourceConfigConvert;
import org.training.cloud.tool.dao.db.DataSourceConfigMapper;
import org.training.cloud.tool.dto.db.AddDataSourceConfigDTO;
import org.training.cloud.tool.dto.db.ModifyDataSourceConfigDTO;
import org.training.cloud.tool.entity.db.DataSourceConfig;

import javax.annotation.Resource;
import java.util.List;

import static org.training.cloud.tool.constant.ToolExceptionEnumConstants.DATA_SOURCE_CONFIG_ERROR;
import static org.training.cloud.tool.constant.ToolExceptionEnumConstants.DATA_SOURCE_CONFIG_NOT_EXISTS;

/**
 * 数据源配置
 *
 * @author wangtongzhou
 * @since 2024-02-25 21:41
 */
@Service
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    @Resource
    private DataSourceConfigMapper dataSourceConfigMapper;

    @Override
    public Long createDataSourceConfig(AddDataSourceConfigDTO addDataSourceConfigDTO) {
        DataSourceConfig config = DataSourceConfigConvert.INSTANCE.convert(addDataSourceConfigDTO);
        checkConnection(config);
        dataSourceConfigMapper.insert(config);
        return config.getId();
    }

    private void checkConnection(DataSourceConfig config) {
        boolean success = JdbcUtils.isConnectionSuccess(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw new BusinessException(DATA_SOURCE_CONFIG_ERROR);
        }
    }

    @Override
    public void updateDataSourceConfig(ModifyDataSourceConfigDTO modifyDataSourceConfigDTO) {
        checkDataSourceConfigExists(modifyDataSourceConfigDTO.getId());
        DataSourceConfig config = DataSourceConfigConvert.INSTANCE.convert(modifyDataSourceConfigDTO);
        checkConnection(config);
        dataSourceConfigMapper.updateById(config);
    }

    private void checkDataSourceConfigExists(Long id) {
        if (dataSourceConfigMapper.selectById(id) == null) {
            throw new BusinessException(DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public void deleteDataSourceConfig(Long id) {
        checkDataSourceConfigExists(id);
        dataSourceConfigMapper.deleteById(id);
    }

    @Override
    public DataSourceConfig getDataSourceConfig(Long id) {
        return dataSourceConfigMapper.selectById(id);
    }

    @Override
    public List<DataSourceConfig> getDataSourceConfigList() {
        return dataSourceConfigMapper.selectList();
    }
}
