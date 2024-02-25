package org.training.cloud.tool.dto.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 新增数据源
 *
 * @author wangtongzhou
 * @since 2024-02-25 21:45
 */
@Schema(description = "数据源配置创建")
@Data
public class AddDataSourceConfigDTO {

    @Schema(description = "数据源名称",  example = "test")
    @NotNull(message = "数据源名称不能为空")
    private String name;

    @Schema(description = "数据源连接", example = "jdbc:mysql://127.0.0.1:3306/system")
    @NotNull(message = "数据源连接不能为空")
    private String url;

    @Schema(description = "用户名",  example = "root")
    @NotNull(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码",example = "123456")
    @NotNull(message = "密码不能为空")
    private String password;
}
