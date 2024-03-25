package org.training.cloud.tool.dao.generator;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.tool.dto.generator.table.GeneratorTableDTO;
import org.training.cloud.tool.entity.generator.ToolGeneratorTable;

import java.util.List;

/**
 * 代码生成表
 *
 * @author wangtongzhou
 * @since 2024-02-27 20:09
 */
@Mapper
public interface GeneratorTableMapper extends BaseMapperExtend<ToolGeneratorTable> {
    default ToolGeneratorTable selectByTableNameAndDataSourceConfigId(String tableName, Long dataSourceConfigId) {
        return selectOne(ToolGeneratorTable::getTableName, tableName,
                ToolGeneratorTable::getDataSourceConfigId, dataSourceConfigId);
    }

    default PageResponse<ToolGeneratorTable> selectPage(GeneratorTableDTO generatorTableDTO) {
        return selectPage(generatorTableDTO, new LambdaQueryWrapperExtend<ToolGeneratorTable>()
                .likeIfPresent(ToolGeneratorTable::getTableName, generatorTableDTO.getTableName())
                .likeIfPresent(ToolGeneratorTable::getTableComment, generatorTableDTO.getTableComment())
                .likeIfPresent(ToolGeneratorTable::getClassName,
                        generatorTableDTO.getClassName()));
    }

    default List<ToolGeneratorTable> selectListByDataSourceConfigId(Long dataSourceConfigId) {
        return selectList(ToolGeneratorTable::getDataSourceConfigId, dataSourceConfigId);
    }

//    default List<ToolGeneratorTable> selectListByTemplateTypeAndMasterTableId(Integer templateType, Long masterTableId) {
//        return selectList(ToolGeneratorTable::getTemplateType, templateType,
//                ToolGeneratorTable::getMasterTableId, masterTableId);
//    }
}
