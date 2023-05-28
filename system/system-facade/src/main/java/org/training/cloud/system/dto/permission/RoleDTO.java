package org.training.cloud.system.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

import javax.validation.constraints.NotEmpty;

/**
 * 角色信息
 *
 * @author wangtongzhou
 * @since 2023-05-28 17:07
 */
@Data
@Accessors(chain = true)
@Schema(description = "查询角色信息")
public class RoleDTO extends PageParam {

    /**
     * name
     */
    @Schema(description = "角色名称", required = true, example = "角色名称")
    @NotEmpty(message = "角色名称不允许为空")
    private String name;



    @Schema(description = "角色类型 1：管理员角色，0：其他", required = true, example = "1")
    @NotEmpty(message = "角色名称不允许为空")
    private Integer type;

}
