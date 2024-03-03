package org.training.cloud.tool.convert.generator;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.tool.entity.generator.ToolGeneratorTable;

/**
 * 转换table
 *
 * @author wangtongzhou
 * @since 2024-03-03 11:37
 */
@Mapper
public interface GeneratorTableConvert {
    GeneratorTableConvert INSTANCE = Mappers.getMapper(GeneratorTableConvert.class);



    @Mappings({
            @Mapping(source = "name", target = "tableName"),
            @Mapping(source = "comment", target = "tableComment"),
    })
    ToolGeneratorTable convert(TableInfo bean);




}
