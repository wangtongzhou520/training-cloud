package com.springcloud.study.system.entity.dept;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springcloud.study.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门实体类
 *
 * @author wangtongzhou
 * @since 2020-06-03 21:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_dept")
public class SysDept extends BaseDO {
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
}
