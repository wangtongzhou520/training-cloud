package org.training.cloud.system.dto.dept;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 修改用户的实体
 *
 * @author wangtongzhou
 * @since 2020-08-07 17:52
 */
@Data
@Accessors(chain = true)
public class UpdateDeptDTO implements Serializable {
    /**
     * 部门id
     */
    @ApiModelProperty(value = "部门id")
    private Integer id;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", required = true, example = "**部门")
    @NotEmpty(message = "部门信息不能为空")
    private String name;

    /**
     * 上级部门id
     */
    @ApiModelProperty(value = "上级部门id", required = true, example = "**部门")
    @NotEmpty(message = "上级部门id不能为空")
    private Integer parentId;

    /**
     * 部门在当前层级下的顺序，由小到大
     */
    @ApiModelProperty(value = "当前层级的排序", example = "1")
    private Integer seq;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注", example = "1")
    private String remark;
}
