package org.training.cloud.tool.service.generator;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.tool.dao.generator.GeneratorColumnMapper;
import org.training.cloud.tool.dao.generator.GeneratorTableMapper;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.dto.generator.AddGeneratorDTO;
import org.training.cloud.tool.dto.generator.ModifyGeneratorDTO;
import org.training.cloud.tool.entity.generator.ToolGeneratorColumn;
import org.training.cloud.tool.entity.generator.ToolGeneratorTable;
import org.training.cloud.tool.enums.generator.GeneratorSceneEnum;
import org.training.cloud.tool.service.db.DatabaseTableService;
import org.training.cloud.tool.utils.GeneratorColumnUtil;
import org.training.cloud.tool.utils.GeneratorTableUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

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
    private GeneratorTableMapper generatorTableMapper;

    @Resource
    private GeneratorColumnMapper generatorColumnMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Long> addGeneratorList(Long userId, AddGeneratorDTO addGeneratorDTO) {

        List<Long> ids = new ArrayList<>(addGeneratorDTO.getTableNames().size());
        addGeneratorDTO.getTableNames().forEach(x ->
                ids.add(addGenerator(addGeneratorDTO.getDataSourceConfigId(), x))
        );
        return null;
    }

    @Override
    public void modifyGenerator(ModifyGeneratorDTO modifyGeneratorDTO) {
        
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
        table.setScene(GeneratorSceneEnum.ADMIN.getCode());
        //待设置
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
