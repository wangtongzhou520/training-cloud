package org.training.cloud.system.vo.dept;

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

    private Long id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级部门id
     */
    private Long parentId;

    /**
     * 部门层级
     */
    private String level;

    /**
     * 部门在当前层级下的顺序，由小到大
     */
    private Integer seq;

    /**
     * 备注
     */
    private String remark;
}
