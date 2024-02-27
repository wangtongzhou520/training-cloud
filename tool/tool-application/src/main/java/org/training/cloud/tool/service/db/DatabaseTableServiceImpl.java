package org.training.cloud.tool.service.db;

import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.entity.db.ToolDataSourceConfig;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据库表
 *
 * @author wangtongzhou
 * @since 2024-02-26 21:13
 */
@Service
public class DatabaseTableServiceImpl implements DatabaseTableService {

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @Override
    public List<TableInfo> getTableList(DatabaseTableDTO databaseTableDTO) {
        List<TableInfo> tables = getAllTable(databaseTableDTO.getDataSourceConfigId(), null);
        return tables.stream().filter(tableInfo -> (StringUtils.isEmpty(databaseTableDTO.getTableName()) || tableInfo.getName().contains(databaseTableDTO.getTableName()))
                        && (StringUtils.isEmpty(databaseTableDTO.getTableDesc()) || tableInfo.getComment().contains(databaseTableDTO.getTableDesc())))
                .collect(Collectors.toList());
    }

    @Override
    public TableInfo getTable(DatabaseTableDTO databaseTableDTO) {
        List<TableInfo> tables = getAllTable(databaseTableDTO.getDataSourceConfigId(),
                databaseTableDTO.getTableName());
        if (CollectionUtils.isEmpty(tables)) {
            return null;
        }
        return tables.stream().findFirst().orElse(null);
    }

    private List<TableInfo> getAllTable(Long dataSourceConfigId, String tableName) {
        // 获得数据源配置
        ToolDataSourceConfig config =
                dataSourceConfigService.getDataSourceConfig(dataSourceConfigId);

        // 使用 MyBatis Plus Generator 解析表结构
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(config.getUrl(), config.getUsername(),
                config.getPassword()).build();
        StrategyConfig.Builder strategyConfig = new StrategyConfig.Builder();
        if (StringUtils.isNotEmpty(tableName)) {
            strategyConfig.addInclude(tableName);
        }
        GlobalConfig globalConfig = new GlobalConfig.Builder().dateType(DateType.TIME_PACK).build();
        ConfigBuilder builder = new ConfigBuilder(null, dataSourceConfig, strategyConfig.build(),
                null, globalConfig, null);
        List<TableInfo> tables = builder.getTableInfoList();
        tables.sort(Comparator.comparing(TableInfo::getName));
        return tables;
    }
}
