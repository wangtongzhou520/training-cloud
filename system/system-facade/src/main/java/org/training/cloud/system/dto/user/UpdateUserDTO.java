package org.training.cloud.system.dto.user;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
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
@ApiOperation(value = "更新用户")
public class UpdateUserDTO implements Serializable {
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Integer id;

    /**
     * 用户名称
     */
    @ApiModelProperty(value = "用户名称", required = true, example = "xxxx")
    @NotEmpty(message = "用户名称不能为空")
    private String username;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true, example = "12345678900")
    @NotEmpty(message = "手机号不能为空")
    private String telephone;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱", required = true, example = "xxx@xxx.com")
    @NotEmpty(message = "邮箱不能为空")
    private String mail;

    /**
     * 用户所在部门的id
     */
    @ApiModelProperty(value = "部门id", required = true, example = "123")
    @NotEmpty(message = "部门id不能为空")
    private Integer deptId;

    /**
     * 状态，1：正常，0：冻结状态，2：删除
     */
    @ApiModelProperty(value = "状态", required = true, example = "0")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "1")
    private String remark;
}
