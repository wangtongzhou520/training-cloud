package org.training.cloud.system.dto.permission;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 新增角色
 *
 * @author wangtongzhou
 */
@Data
@Accessors(chain = true)
@Schema(description = "新增角色")
public class AddRoleDTO implements Serializable {
    /**
     * name
     */
    @Schema(description = "角色名称", required = true, example = "角色名称")
    @NotBlank(message = "角色名称不允许为空")
    private String name;


    @Schema(description = "角色标识", required = true, example = "角色标识")
    @NotBlank(message = "角色名称不允许为空")
    private String code;


    @Schema(description = "角色类型 1：管理员角色，0：其他", required = true, example = "1")
    @NotNull(message = "角色名称不允许为空")
    private Integer type;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "备注")
    private String remark;
}
