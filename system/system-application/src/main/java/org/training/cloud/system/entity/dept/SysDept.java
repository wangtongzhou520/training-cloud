package org.training.cloud.system.entity.dept;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * 部门实体类
 *
 * @author wangtongzhou
 * @since 2020-06-03 21:44
 */
@Data
@TableName(value = "sys_dept")
public class SysDept extends BaseDO {
    /**
     * 部门id
     */
    @TableId(type = IdType.AUTO)
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


    /**
     * 管理者ID
     */
    private Long manageId;


}
