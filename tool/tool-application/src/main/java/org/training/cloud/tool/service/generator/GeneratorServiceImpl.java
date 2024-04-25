package org.training.cloud.tool.service.generator;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.convert.generator.GeneratorColumnConvert;
import org.training.cloud.tool.convert.generator.GeneratorTableConvert;
import org.training.cloud.tool.dao.generator.GeneratorColumnMapper;
import org.training.cloud.tool.dao.generator.GeneratorTableMapper;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.dto.generator.AddGeneratorDTO;
import org.training.cloud.tool.dto.generator.ModifyGeneratorDTO;
import org.training.cloud.tool.dto.generator.table.GeneratorTableDTO;
import org.training.cloud.tool.entity.db.ToolDataSourceConfig;
import org.training.cloud.tool.entity.generator.ToolGeneratorColumn;
import org.training.cloud.tool.entity.generator.ToolGeneratorTable;
import org.training.cloud.tool.service.db.DataSourceConfigService;
import org.training.cloud.tool.service.db.DatabaseTableService;
import org.training.cloud.tool.utils.GeneratorColumnUtil;
import org.training.cloud.tool.utils.GeneratorTableUtil;
import org.training.cloud.tool.vo.db.DatabaseTableVO;
import org.training.cloud.tool.vo.generator.GeneratorVO;
import org.training.cloud.tool.vo.generator.table.GeneratorTableVO;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import static org.training.cloud.tool.constant.ToolExceptionEnumConstants.*;

/**
 * 代码生成器
 *
 * @author wangtongzhou
 * @since 2024-02-27 20:52
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    @Resource
    private DatabaseTableService databaseTableService;

    @Resource
    private DataSourceConfigService dataSourceConfigService;

    @Resource
    private GeneratorTableMapper generatorTableMapper;

    @Resource
    private GeneratorColumnMapper generatorColumnMapper;

    @Resource
    private GeneratorTableUtil generatorTableUtil;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addGeneratorList(AddGeneratorDTO addGeneratorDTO) {

        List<Long> ids = new ArrayList<>(addGeneratorDTO.getTableNames().size());
        addGeneratorDTO.getTableNames().forEach(x ->
                ids.add(addGenerator(addGeneratorDTO.getDataSourceConfigId(), x))
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyGenerator(ModifyGeneratorDTO modifyGeneratorDTO) {
        // 校验是否已经存在
        checkTableExistsById(modifyGeneratorDTO.getTable().getId());

        ToolGeneratorTable toolGeneratorTable =
                GeneratorTableConvert.INSTANCE.convert(modifyGeneratorDTO.getTable());
        generatorTableMapper.updateById(toolGeneratorTable);


        List<ToolGeneratorColumn> toolGeneratorColumns =
                GeneratorColumnConvert.INSTANCE.convert(modifyGeneratorDTO.getColumns());
        toolGeneratorColumns.forEach(x -> {
            generatorColumnMapper.updateById(x);
        });
    }

    @Override
    public List<DatabaseTableVO> queryGeneratorTableList(DatabaseTableDTO databaseTableDTO) {

        List<TableInfo> tableInfos = databaseTableService.getTableList(databaseTableDTO);
        //查询已经生成过的表
        List<ToolGeneratorTable> toolGeneratorTables =
                generatorTableMapper.selectListByDataSourceConfigId(databaseTableDTO.getDataSourceConfigId());
        Set<String> tableNames = CollectionExtUtils.convertSet(toolGeneratorTables,
                ToolGeneratorTable::getTableName);
        tableInfos.removeIf(x -> tableNames.contains(x.getName()));
        return GeneratorTableConvert.INSTANCE.convertList(tableInfos);
    }

    @Override
    public GeneratorVO queryGeneratorDetail(Long tableId) {
        ToolGeneratorTable toolGeneratorTable = generatorTableMapper.selectById(tableId);
        List<ToolGeneratorColumn> toolGeneratorColumns = generatorColumnMapper.selectListByTableId(tableId);
        GeneratorVO generatorVO = new GeneratorVO();
        generatorVO.setGeneratorTable(GeneratorTableConvert.INSTANCE.converVO(toolGeneratorTable));
        generatorVO.setGeneratorColumns(GeneratorColumnConvert.INSTANCE.convertListVO(toolGeneratorColumns));
        return generatorVO;
    }

    @Override
    public PageResponse<GeneratorTableVO> pageGeneratorTable(GeneratorTableDTO generatorTableDTO) {
        PageResponse<ToolGeneratorTable> pageResponse = generatorTableMapper.selectPage(generatorTableDTO);
        //转换
        PageResponse<GeneratorTableVO> result = GeneratorTableConvert.INSTANCE.convertPage(pageResponse);
        if (CollectionUtils.isNotEmpty(result.getList())) {
            //查询数据源名称
            Set<Long> dataSourceIds = CollectionExtUtils.convertSet(result.getList(),
                    GeneratorTableVO::getDataSourceConfigId);
            List<ToolDataSourceConfig> toolDataSourceConfigs =
                    dataSourceConfigService.getDataSourceConfigByIds(dataSourceIds);
            Map<Long, String> dataSourceMap = toolDataSourceConfigs.stream()
                    .collect(Collectors.toMap(ToolDataSourceConfig::getId, ToolDataSourceConfig::getName));
            result.getList().forEach(x -> {
                String dataSourceName = dataSourceMap.get(x.getDataSourceConfigId());
                if (StringUtils.isNotBlank(dataSourceName)) {
                    x.setDataSourceConfigName(dataSourceName);
                }
            });
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncGeneratorDB(Long tableId) {
        ToolGeneratorTable toolGeneratorTable = checkTableExistsById(tableId);
        DatabaseTableDTO databaseTableDTO = new DatabaseTableDTO();
        databaseTableDTO.setDataSourceConfigId(toolGeneratorTable.getDataSourceConfigId());
        databaseTableDTO.setTableName(toolGeneratorTable.getTableName());
        TableInfo tableInfo = databaseTableService.getTable(databaseTableDTO);
        checkTable(tableInfo);
        List<TableField> tableFields = tableInfo.getFields();

        //字段名
        List<ToolGeneratorColumn> generatorColumns =
                generatorColumnMapper.selectListByTableId(tableId);
        Map<String, ToolGeneratorColumn> generatorColumnMap =
                CollectionExtUtils.convertMap(generatorColumns, ToolGeneratorColumn::getColumnName);

        //条件检查
        BiPredicate<TableField, ToolGeneratorColumn> filter =
                (tableField, generatorColumn) -> tableField.getMetaInfo().getJdbcType().name().equals(generatorColumn.getDataType())
                        && tableField.getMetaInfo().isNullable() == generatorColumn.getNullable()
                        && tableField.isKeyFlag() == generatorColumn.getPrimaryKey()
                        && tableField.getComment().equals(generatorColumn.getColumnComment());
        //需要修改
        Set<String> modifyFieldNames = tableFields.stream()
                .filter(x -> generatorColumnMap.get(x.getColumnName()) != null
                        && !filter.test(x, generatorColumnMap.get(x.getColumnName()))
                ).map(TableField::getColumnName).collect(Collectors.toSet());
        //需要删除
        Set<String> tableFieldNames = CollectionExtUtils.convertSet(tableFields,
                TableField::getName);
        Set<Long> deleteColumnIds = generatorColumns.stream()
                .filter(x -> (!tableFieldNames.contains(x.getColumnName())) || modifyFieldNames.contains(x.getColumnName()))
                .map(ToolGeneratorColumn::getId).collect(Collectors.toSet());

        //移除已经存在的字段
        tableFields.removeIf(x -> generatorColumns.contains(x.getColumnName()) && (!modifyFieldNames.contains(x.getColumnName())));
        if (CollectionUtils.isEmpty(tableFields) && CollectionUtils.isEmpty(deleteColumnIds)) {
            throw new BusinessException(GENERATOR_SYNC_NOT_CHANGE);
        }

        // 插入新增的字段
        List<ToolGeneratorColumn> columns = GeneratorColumnUtil.buildColumns(tableId, tableFields);
        generatorColumnMapper.insertBatch(columns);
        // 删除不存在的字段
        if (CollectionUtils.isNotEmpty(deleteColumnIds)) {
            generatorColumnMapper.deleteBatchIds(deleteColumnIds);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delGenerator(Long tableId) {
        checkTableExistsById(tableId);
        generatorTableMapper.deleteById(tableId);
        generatorColumnMapper.deleteListByTableId(tableId);
    }

    @Override
    public Map<String, String> previewGeneratorCode(Long tableId) {
        ToolGeneratorTable table = checkTableExistsById(tableId);
        List<ToolGeneratorColumn> columns = generatorColumnMapper.selectListByTableId(tableId);
        if (CollectionUtils.isEmpty(columns)) {
            throw new BusinessException(GENERATOR_COLUMN_NOT_EXISTS);
        }
        return generatorTableUtil.execute(table, columns);
    }


    private ToolGeneratorTable checkTableExistsById(Long tableId) {
        ToolGeneratorTable generatorTable = generatorTableMapper.selectById(tableId);
        if (Objects.isNull(generatorTable)) {
            throw new BusinessException(GENERATOR_TABLE_NULL);
        }
        return generatorTable;
    }

    private Long addGenerator(Long dataSourceConfigId, String tableName) {
        DatabaseTableDTO databaseTableDTO = new DatabaseTableDTO();
        databaseTableDTO.setDataSourceConfigId(dataSourceConfigId);
        databaseTableDTO.setTableName(tableName);
        //从数据库中，获得数据库表结构
        TableInfo tableInfo = databaseTableService.getTable(databaseTableDTO);
        checkTable(tableInfo);
        //校验表是否已生成
        checkTableExists(tableName, dataSourceConfigId);

        ToolGeneratorTable table = GeneratorTableUtil.buildTable(tableInfo);
        table.setDataSourceConfigId(dataSourceConfigId);
        //TODO 待设置
        table.setAuthor("");
        generatorTableMapper.insert(table);

        //构建对应表的列
        List<ToolGeneratorColumn> columns = GeneratorColumnUtil.buildColumns(table.getId(), tableInfo.getFields());
        //如果没有主键，则使用第一个字段作为主键
        if (!tableInfo.isHavePrimaryKey()) {
            columns.get(0).setPrimaryKey(true);
        }
        generatorColumnMapper.insertBatch(columns);
        return table.getId();

    }

    private void checkTable(TableInfo tableInfo) {
        if (tableInfo == null) {
            throw new BusinessException(GENERATOR_TABLE_NULL);
        }
        if (CollectionUtils.isEmpty(tableInfo.getFields())) {
            throw new BusinessException(GENERATOR_TABLE_COLUMN_NULL);
        }
        if (StringUtils.isEmpty(tableInfo.getComment())) {
            throw new BusinessException(GENERATOR_TABLE_COMMENT_NULL);
        }
        tableInfo.getFields().forEach(field -> {
            if (StringUtils.isEmpty(field.getComment())) {
                throw new BusinessException(field.getName() + "注释不存在");
            }
        });
    }

    private void checkTableExists(String tableName, Long dataSourceConfigId) {
        ToolGeneratorTable toolGeneratorTable = generatorTableMapper
                .selectByTableNameAndDataSourceConfigId(tableName, dataSourceConfigId);
        if (toolGeneratorTable != null) {
            throw new BusinessException(GENERATOR_TABLE_EXISTS);
        }
    }
}
