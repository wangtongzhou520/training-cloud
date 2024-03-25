package org.training.cloud.tool.convert.generator;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.tool.dto.generator.table.ModifyGeneratorColumnDTO;
import org.training.cloud.tool.entity.generator.ToolGeneratorColumn;
import org.training.cloud.tool.vo.generator.column.GeneratorColumnVO;

import java.util.List;

/**
 * 生成列
 *
 * @author wangtongzhou 18635604249
 * @since 2024-03-03 16:25
 */
@Mapper
public interface GeneratorColumnConvert {

    GeneratorColumnConvert INSTANCE = Mappers.getMapper(GeneratorColumnConvert.class);


    List<ToolGeneratorColumn> convertList(List<TableField> list);

    @Mappings({
            @Mapping(source = "name", target = "columnName"),
            @Mapping(source = "metaInfo.jdbcType", target = "dataType"),
            @Mapping(source = "comment", target = "columnComment"),
            @Mapping(source = "metaInfo.nullable", target = "nullable"),
            @Mapping(source = "keyFlag", target = "primaryKey"),
            @Mapping(source = "keyIdentityFlag", target = "autoIncrement"),
            @Mapping(source = "columnType.type", target = "javaType"),
            @Mapping(source = "propertyName", target = "javaField"),
    })
    ToolGeneratorColumn convert(TableField bean);


    ToolGeneratorColumn convert(ModifyGeneratorColumnDTO modifyGeneratorColumnDTO);

    List<ToolGeneratorColumn> convert(List<ModifyGeneratorColumnDTO> modifyGeneratorColumns);

    GeneratorColumnVO convertVO(ToolGeneratorColumn toolGeneratorColumn);

    List<GeneratorColumnVO> convertListVO(List<ToolGeneratorColumn> toolGeneratorColumns);

}
