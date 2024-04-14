package org.training.cloud.tool.convert.generator;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.dto.generator.table.ModifyGeneratorTableDTO;
import org.training.cloud.tool.entity.generator.ToolGeneratorTable;
import org.training.cloud.tool.vo.db.DatabaseTableVO;
import org.training.cloud.tool.vo.generator.GeneratorPreviewCodeVO;
import org.training.cloud.tool.vo.generator.table.GeneratorTableVO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    GeneratorTableVO converVO(ToolGeneratorTable toolGeneratorTable);


    ToolGeneratorTable convert(ModifyGeneratorTableDTO table);


    DatabaseTableVO convertVO(TableInfo tableInfo);


    List<DatabaseTableVO> convertList(List<TableInfo> tableInfos);


    PageResponse<GeneratorTableVO> convertPage( PageResponse<ToolGeneratorTable> pageResponse);

    default List<GeneratorPreviewCodeVO> convert(Map<String, String> codeMap){
        return codeMap.entrySet().stream().map(entry -> {
            GeneratorPreviewCodeVO vo = new GeneratorPreviewCodeVO();
            vo.setCode(entry.getValue());
            vo.setFilePath(entry.getKey());
            return vo;
        }).collect(Collectors.toList());
    }

}
