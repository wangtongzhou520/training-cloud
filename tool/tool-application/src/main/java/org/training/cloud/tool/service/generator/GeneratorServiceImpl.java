package org.training.cloud.tool.service.generator;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.dto.generator.AddGeneratorDTO;
import org.training.cloud.tool.service.db.DatabaseTableService;

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

    @Override
    public List<Long> addGeneratorList(Long userId, AddGeneratorDTO addGeneratorDTO) {

        List<Long> ids = new ArrayList<>(addGeneratorDTO.getTableNames().size());


        return null;
    }


    private Long addGenerator(Long userId, Long dataSourceConfigId, String tableName) {
        DatabaseTableDTO databaseTableDTO = new DatabaseTableDTO();
        databaseTableDTO.setDataSourceConfigId(dataSourceConfigId);
        databaseTableDTO.setTableName(tableName);
        // 从数据库中，获得数据库表结构
        TableInfo tableInfo = databaseTableService.getTable(databaseTableDTO);
        checkTable(tableInfo);

        return 1L;
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


    private void checkTableExists(){

    }
}
