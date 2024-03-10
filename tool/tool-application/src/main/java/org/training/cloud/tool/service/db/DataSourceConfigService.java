package org.training.cloud.tool.service.db;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.dto.db.AddDataSourceConfigDTO;
import org.training.cloud.tool.dto.db.DataSourceConfigDTO;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.dto.db.ModifyDataSourceConfigDTO;
import org.training.cloud.tool.entity.db.ToolDataSourceConfig;
import org.training.cloud.tool.vo.db.DataSourceConfigVO;
import org.training.cloud.tool.vo.db.DatabaseTableVO;

import java.util.List;

/**
 * 数据源配置
 *
 * @author wangtongzhou
 * @since 2024-02-25 21:40
 */
public interface DataSourceConfigService {


    /**
     * 创建数据源配置
     *
     * @param addDataSourceConfigDTO
     * @return
     */
    Long addDataSourceConfig(AddDataSourceConfigDTO addDataSourceConfigDTO);

    /**
     * 更新数据源配置
     *
     * @param modifyDataSourceConfigDTO
     */
    void updateDataSourceConfig(ModifyDataSourceConfigDTO modifyDataSourceConfigDTO);

    /**
     * 分页查询数据源配置
     *
     * @param dataSourceConfigDTO
     * @return
     */
    PageResponse<DataSourceConfigVO> pageDataSourceConfig(DataSourceConfigDTO dataSourceConfigDTO);

    /**
     * 删除数据源配置
     *
     * @param id
     */
    void deleteDataSourceConfig(Long id);

    /**
     * 获得数据源配置
     *
     * @param id
     * @return
     */
    ToolDataSourceConfig getDataSourceConfig(Long id);

    /**
     * 获得数据源配置列表
     *
     * @return
     */
    List<ToolDataSourceConfig> getDataSourceConfigList();
}
