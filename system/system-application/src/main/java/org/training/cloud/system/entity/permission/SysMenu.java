package org.training.cloud.system.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * 菜单
 *
 * @author wangtongzhou
 * @since 2023-05-30 21:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_menu")
public class SysMenu extends BaseDO {

    @TableId(type = IdType.AUTO)
    /**
     * 唯一键
     */
    private Long id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 菜单类型
     */
    private int type;

    /**
     * 显示顺序
     */
    private Integer sort;

    /**
     * 父菜单id
     */
    private Long parentId;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 组件路径
     */
    private String component;
    

}
