package org.training.cloud.tool.dto.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改数据源信息
 *
 * @author wangtongzhou
 * @since 2024-02-25 21:52
 */
@Schema(description = "修改数据源配置")
@Data
public class ModifyDataSourceConfigDTO implements Serializable {

    @Schema(description = "主键编号", example = "1024")
    @NotNull(message = "数据源主键不能为空")
    private Long id;

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
