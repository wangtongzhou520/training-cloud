package org.training.cloud.system.vo.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 部门返回列表
 *
 * @author wangtongzhou
 * @since 2024-01-05 21:23
 */
@Data
public class DeptVO implements Serializable {


    @Schema(description = "部门ID", required = true, example = "1")
    private Long id;

    /**
     * 部门名称
     */
    @Schema(description = "部门名称", required = true, example = "中国")
    private String name;

    /**
     * 上级部门id
     */
    @Schema(description = "上级部门id", required = true, example = "2")
    private Long parentId;

    /**
     * 部门层级
     */
    @Schema(description = "部门层级", required = true, example = "0.1")
    private String level;

    /**
     * 部门在当前层级下的顺序，由小到大
     */
    @Schema(description = "部门在当前层级下的顺序", required = true, example = "1")
    private Integer seq;

    /**
     * 备注
     */
    @Schema(description = "备注", required = true, example = "1")
    private String remark;


    @Schema(description = "管理者ID", required = true, example = "1")
    private Long manageId;


    @Schema(description = "管理者", required = true, example = "1")
    private String manageName;


    @Schema(description = "展示状态", required = true, example = "true")
    private Boolean deleteState;

}
