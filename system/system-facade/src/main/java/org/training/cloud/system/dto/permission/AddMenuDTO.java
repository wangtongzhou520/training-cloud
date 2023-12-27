package org.training.cloud.system.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 新增菜单
 *
 * @author wangtongzhou 18635604249
 * @since 2023-05-31 07:45
 */
@Data
@Accessors(chain = true)
@Schema(description = "新增菜单")
public class AddMenuDTO implements Serializable {

    @Schema(description = "菜单名称", required = true, example = "芋道")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 50, message = "菜单长度不能超过50个字符")
    private String name;

    @Schema(description = "权限标识 菜单和按钮类型时候传", example = "sys:menu:add")
    @Size(max = 100)
    private String permission;

    @Schema(description = "菜单类型 1 目录 2 菜单 3按钮", required = true, example = "1")
    @NotNull(message = "菜单类型不能为空")
    private Integer type;

    @Schema(description = "显示顺序不能为空", required = true, example = "1")
    @NotNull(message = "显示顺序不能为空")
    private Integer sort;

    @Schema(description = "父ID", required = true, example = "1")
    @NotNull(message = "父ID不能为空")
    private Long parentId;

    @Schema(description = "路由地址", example = "post")
    @Size(max = 200, message = "路由地址不能超过200个字符")
    private String path;

    @Schema(description = "菜单图标", example = "/menu/list")
    private String icon;

    @Schema(description = "组件路径", example = "system/post/index")
    @Size(max = 255, message = "组件路径不能超过255个字符")
    private String component;

}
