package com.springcloud.study.system.entity.role;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springcloud.study.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色
 *
 * @author wangtongzhou
 * @since 2020-11-09 14:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_role")
public class SysRole extends BaseDO {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * 角色的类型，1：管理员角色，2：其他
     */
    private Boolean type;

    /**
     * 状态，1：可用，0：冻结
     */
    private Boolean status;

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
     * 修改者ip
     */
    private String modifiedOperatorIp;
}
