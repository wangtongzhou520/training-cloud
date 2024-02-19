package org.training.cloud.system.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 新增菜单的角色列表
 *
 * @author wangtongzhou
 * @since 2023-12-24 16:44
 */
@Data
@Accessors(chain = true)
@Schema(description = "新增菜单的角色列表")
public class AddRoleMenuDTO implements Serializable {
    @Schema(description = "角色编号", required = true, example = "2")
    @NotNull(message = "角色编号不能为空")
    private Long roleId;

    @Schema(description = "权限编号列表", required = true, example = "1,3,5")
    @NotNull(message = "权限编号列表不能为空")
    private List<Long> menuIds;
}
