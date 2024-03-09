package org.training.cloud.tool.dto.generator;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.training.cloud.tool.dto.generator.table.ModifyGeneratorColumnDTO;
import org.training.cloud.tool.dto.generator.table.ModifyGeneratorTableDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 修改代码生成器的表和字段定义
 *
 * @author wangtongzhou
 * @since 2024-03-03 17:38
 */
@Schema(description = "修改代码生成器的表和字段定义")
@Data
public class ModifyGeneratorDTO implements Serializable {

    @Valid
    @NotNull(message = "表定义不能为空")
    private ModifyGeneratorTableDTO table;

    @Valid
    @NotNull(message = "字段定义不能为空")
    private List<ModifyGeneratorColumnDTO> columns;
}
