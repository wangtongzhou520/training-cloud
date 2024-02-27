package org.training.cloud.tool.dao.generator;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.tool.entity.generator.ToolGeneratorColumn;

import java.util.List;

/**
 * 代码生成列
 *
 * @author wangtongzhou
 * @since 2024-02-27 20:35
 */
@Mapper
public interface GeneratorColumnMapper extends BaseMapperExtend<ToolGeneratorColumn> {


    default List<ToolGeneratorColumn> selectListByTableId(Long tableId) {
        return selectList(new LambdaQueryWrapperExtend<ToolGeneratorColumn>()
                .eq(ToolGeneratorColumn::getTableId, tableId)
                .orderByAsc(ToolGeneratorColumn::getId));
    }

    default void deleteListByTableId(Long tableId) {
        delete(new LambdaQueryWrapperExtend<ToolGeneratorColumn>()
                .eq(ToolGeneratorColumn::getTableId, tableId));
    }
}
