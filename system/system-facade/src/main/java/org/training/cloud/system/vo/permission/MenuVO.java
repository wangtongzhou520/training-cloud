package org.training.cloud.system.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单列表
 *
 * @author wangtongzhou
 * @since 2023-06-01 21:47
 */
@Data
public class MenuVO implements Serializable {
    @Schema(description = "菜单编号", required = true, example = "1")
    private Long id;

    @Schema(description = "菜单名称", required = true, example = "用户管理")
    private String name;

    @Schema(description = "父菜单ID", required = true, example = "1")
    private Long parentId;

    @Schema(description = "菜单类型 1 目录 2 菜单 3按钮", required = true, example = "1")
    private Integer type;

    @Schema(description = "权限标识", required = true, example = "1")
    private String permission;
    
}
