package org.training.cloud.system.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.*;
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
public class ModifyUserDTO implements Serializable {
    /**
     * 用户id
     */
    @Schema(description = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long id;

    /**
     * 用户名称
     */
    @Schema(description = "用户名称", required = true, example = "xxxx")
    @NotBlank(message = "用户账号不能为空")
//    @Pattern(regexp = "^[a-zA-Z0-9]{4,30}$", message = "用户账号由 数字、字母 组成")
    @Size(min = 4, max = 30, message = "用户账号长度为 4-30 个字符")
    private String username;

    /**
     * 手机号
     */
    @Schema(description = "手机号", required = true, example = "12345678900")
    @NotBlank(message = "手机号不能为空")
    private String telephone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", required = true, example = "xxx@xxx.com")
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过 50 个字符")
    private String mail;

    /**
     * 用户所在部门的id
     */
    @Schema(description = "部门id", required = true, example = "123")
    @NotNull(message = "部门id不能为空")
    private Long deptId;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "1")
    private String remark;
}
