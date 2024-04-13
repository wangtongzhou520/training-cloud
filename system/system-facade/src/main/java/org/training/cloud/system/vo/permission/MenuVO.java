package org.training.cloud.system.vo.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

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

    @Schema(description = "是否可见", required = true, example = "true or false")
    private Boolean visible;

    @Schema(description = "路由地址", required = true)
    private String path;

    @Schema(description = "组件路径", required = true)
    private String component;

    @Schema(description = "组件名称", required = true)
    private String componentName;

    @Schema(description = "显示顺序", required = true)
    private Integer sort;

}
