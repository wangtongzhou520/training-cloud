package org.training.cloud.tool.dto.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据表
 *
 * @author wangtongzhou
 * @since 2024-02-26 21:14
 */
@Schema(description = "数据表查询")
@Data
public class DatabaseTableDTO implements Serializable {

    @Schema(description = "数据库连接id", example = "1")
    private Long dataSourceConfigId;

    @Schema(description = "表名", example = "sys_user")
    private String tableName;

    @Schema(description = "表描述", example = "sys_user")
    private String tableDesc;
}
