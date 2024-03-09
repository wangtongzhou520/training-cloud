package org.training.cloud.tool.dto.generator.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.training.cloud.common.core.vo.PageParam;

/**
 * 表单分页
 *
 * @author wangtongzhou
 * @since 2024-03-09 17:28
 */
@Data
public class GeneratorTableDTO extends PageParam {

    @Schema(description = "表名称", example = "sys_user")
    private String tableName;

    @Schema(description = "表描述", example = "用户")
    private String tableComment;

    @Schema(description = "实体名", example = "SysUser")
    private String className;
}
