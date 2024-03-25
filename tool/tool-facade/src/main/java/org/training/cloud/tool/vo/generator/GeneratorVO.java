package org.training.cloud.tool.vo.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.training.cloud.tool.vo.generator.column.GeneratorColumnVO;
import org.training.cloud.tool.vo.generator.table.GeneratorTableVO;

import java.io.Serializable;
import java.util.List;

/**
 * 代码生成详情
 *
 * @author wangtongzhou
 * @since 2024-03-09 17:10
 */
@Data
public class GeneratorVO implements Serializable {

    @Schema(description = "表定义")
    private GeneratorTableVO generatorTable;


    @Schema(description = "列定义")
    private List<GeneratorColumnVO> generatorColumns;

}
