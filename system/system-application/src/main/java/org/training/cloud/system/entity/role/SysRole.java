package org.training.cloud.system.entity.role;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

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
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * name
     */
    private String name;

    /**
     * 角色的数据权限，1：管理员角色，2：其他
     */
    private Boolean type;

    /**
     * 状态，1：可用，0：冻结
     */
    private Boolean status;

    /**
     * 角色权限字符串
     */
    private String code;

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
}
