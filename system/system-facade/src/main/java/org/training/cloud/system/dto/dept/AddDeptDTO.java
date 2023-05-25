package org.training.cloud.system.dto.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 添加部门
 *
 * @author wangtongzhou
 * @since 2020-06-15 20:53
 */
@Data
@Accessors(chain = true)
public class AddDeptDTO implements Serializable {

    /**
     * 部门名称
     */
    @Schema(description = "部门名称", required = true, example = "**部门")
    @NotEmpty(message = "部门信息不能为空")
    private String name;

    /**
     * 上级部门id
     */
    @Schema(description = "上级部门id", required = true, example = "**部门")
    @NotEmpty(message = "上级部门id不能为空")
    private Long parentId;

    /**
     * 部门在当前层级下的顺序，由小到大
     */
    @Schema(description = "当前层级的排序", example = "1")
    private Integer seq;

    /**
     * 备注
     */
    @Schema(description = "备注", example = "1")
    private String remark;
}