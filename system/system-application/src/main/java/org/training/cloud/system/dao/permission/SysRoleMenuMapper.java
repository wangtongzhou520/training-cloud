package org.training.cloud.system.dao.permission;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.permission.SysRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单信息
 *
 * @author wangtongzhou
 * @since 2023-12-24 16:19
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapperExtend<SysRoleMenu> {

    /**
     * 根据角色ID查询权限信息
     *
     * @param roleId
     * @return
     */
    default List<SysRoleMenu> selectByRoleId(Long roleId) {
        return selectList(SysRoleMenu::getRoleId, roleId);
    }

    /**
     * 根据角色列表获取所有的权限信息
     *
     * @param roleIds
     * @return
     */
    default List<SysRoleMenu> selectByRoleIds(Set<Long> roleIds) {
        return selectList(SysRoleMenu::getRoleId, roleIds);
    }

    /**
     * 删除
     * @param roleId
     * @param result
     */
    default void removeByRoleIdAndMenuIds(Long roleId, Set<Long> result){
        delete(new LambdaQueryWrapperExtend<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId)
                .in(SysRoleMenu::getMenuId, result)
        );
    }


    /**
     * 移除菜单关联的角色信息
     *
     * @param menuId
     */
    default void removeListByMenuId(Long menuId) {
        delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getMenuId, menuId));
    }


    /**
     * 移除角色关联的菜单信息
     *
     * @param roleId
     */
    default void removeListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId,
                roleId));
    }
}
