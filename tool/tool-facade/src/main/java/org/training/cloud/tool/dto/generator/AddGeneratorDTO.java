package org.training.cloud.tool.dto.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 代码生成器
 *
 * @author wangtongzhou
 * @since 2024-02-27 21:02
 */
@Schema(description = "创建代码生成器的表和字段定义")
@Data
public class AddGeneratorDTO {

    @Schema(description = "数据源配置的编号",  example = "1")
    @NotNull(message = "数据源配置的编号不能为空")
    private Long dataSourceConfigId;

    @Schema(description = "表名数组",  example = "[table1, table2, table3]")
    @NotNull(message = "表名数组不能为空")
    private List<String> tableNames;
}
