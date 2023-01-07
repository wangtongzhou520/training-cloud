package com.springcloud.study.system.bo.dept;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 查询返回参数
 *
 * @author wangtongzhou
 * @since 2020-08-08 14:28
 */
@Data
@Accessors(chain = true)
public class QueryDeptBO {
    /**
     * 部门id
     */
    private Integer id;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 上级部门id
     */
    private Integer parentId;

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

    /**
     * 创建者
     */
    private String createOperator;

    /**
     * 修改者
     */
    private String modifiedOperator;

    /**
     * 更新操作者的ip地址
     */
    private String modifiedOperatorIp;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

}
