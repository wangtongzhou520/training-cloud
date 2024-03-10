package org.training.cloud.tool.service.db;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.util.JdbcUtils;
import org.training.cloud.tool.convert.db.DataSourceConfigConvert;
import org.training.cloud.tool.dao.db.DataSourceConfigMapper;
import org.training.cloud.tool.dto.db.AddDataSourceConfigDTO;
import org.training.cloud.tool.dto.db.DataSourceConfigDTO;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.dto.db.ModifyDataSourceConfigDTO;
import org.training.cloud.tool.entity.db.ToolDataSourceConfig;
import org.training.cloud.tool.vo.db.DataSourceConfigVO;
import org.training.cloud.tool.vo.db.DatabaseTableVO;

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


    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


    @Override
    public Long addDataSourceConfig(AddDataSourceConfigDTO addDataSourceConfigDTO) {
        ToolDataSourceConfig config = DataSourceConfigConvert.INSTANCE.convert(addDataSourceConfigDTO);
        checkConnection(config);
        String encryptedPassword = ENCODER.encode(addDataSourceConfigDTO.getPassword());
        config.setPassword(encryptedPassword);
        dataSourceConfigMapper.insert(config);
        return config.getId();
    }

    private void checkConnection(ToolDataSourceConfig config) {
        boolean success = JdbcUtils.isConnectionSuccess(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw new BusinessException(DATA_SOURCE_CONFIG_ERROR);
        }
    }

    @Override
    public void updateDataSourceConfig(ModifyDataSourceConfigDTO modifyDataSourceConfigDTO) {
        checkDataSourceConfigExists(modifyDataSourceConfigDTO.getId());
        ToolDataSourceConfig config = DataSourceConfigConvert.INSTANCE.convert(modifyDataSourceConfigDTO);
        checkConnection(config);
        String encryptedPassword = ENCODER.encode(modifyDataSourceConfigDTO.getPassword());
        config.setPassword(encryptedPassword);
        dataSourceConfigMapper.updateById(config);
    }

    @Override
    public PageResponse<DataSourceConfigVO> pageDataSourceConfig(DataSourceConfigDTO dataSourceConfigDTO) {
        PageResponse<ToolDataSourceConfig> dataSourceConfigPageResponse = dataSourceConfigMapper.selectPage(dataSourceConfigDTO);
        return DataSourceConfigConvert.INSTANCE.convert(dataSourceConfigPageResponse);
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
    public ToolDataSourceConfig getDataSourceConfig(Long id) {
        return dataSourceConfigMapper.selectById(id);
    }

    @Override
    public List<ToolDataSourceConfig> getDataSourceConfigList() {
        return dataSourceConfigMapper.selectList();
    }
}
