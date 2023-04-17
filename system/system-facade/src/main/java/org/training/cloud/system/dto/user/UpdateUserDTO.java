package org.training.cloud.system.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 更新
 *
 * @author wangtongzhou
 * @since 2020-08-14 22:00
 */
@Data
@Accessors(chain = true)
@Schema(description = "更新用户")
public class UpdateUserDTO implements Serializable {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    private Integer id;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称", required = true, example = "xxxx")
    @NotEmpty(message = "用户名称不能为空")
    private String username;

    /**
     * 手机号
     */
    @Schema(description = "手机号", required = true, example = "12345678900")
    @NotEmpty(message = "手机号不能为空")
    private String telephone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", required = true, example = "xxx@xxx.com")
    @NotEmpty(message = "邮箱不能为空")
    private String mail;

    /**
     * 用户所在部门的id
     */
    @Schema(description = "部门id", required = true, example = "123")
    @NotEmpty(message = "部门id不能为空")
    private Integer deptId;

    /**
     * 状态，1：正常，0：冻结状态，2：删除
     */
    @Schema(description = "状态", required = true, example = "0")
    private Integer status;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "1")
    private String remark;
}
