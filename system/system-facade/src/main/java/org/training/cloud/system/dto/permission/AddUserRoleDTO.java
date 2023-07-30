package org.training.cloud.system.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * 新增用户和角色信息
 *
 * @author wangtongzhou 18635604249
 * @since 2023-05-30 21:22
 */
@Data
@Accessors(chain = true)
@Schema(description = "新增用户和角色")
public class AddUserRoleDTO implements Serializable {

    @Schema(description = "角色编号", required = true, example = "2")
    @NotNull(message = "角色编号不能为空")
    private Long userId;

    @Schema(description = "角色编号列表", required = true, example = "1,3,5")
    @NotNull(message = "角色编号列表不能为空")
    private List<Long> roleIds;


}
