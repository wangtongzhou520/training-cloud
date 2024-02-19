package org.training.cloud.system.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * 角色菜单信息
 *
 * @author wangtongzhou
 * @since 2023-12-24 15:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_role_menu")
public class SysRoleMenu extends BaseDO {

    @TableId(type = IdType.AUTO)
    /**
     * 自增编号
     */
    private Long id;

    /**
     * 用户id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;
}
