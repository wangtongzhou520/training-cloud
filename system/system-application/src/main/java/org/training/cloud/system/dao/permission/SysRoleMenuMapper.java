package org.training.cloud.system.dao.permission;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.permission.SysRoleMenu;

import java.util.List;
import java.util.Set;

/**
 * 角色菜单信息
 *
 * @author wangtongzhou 18635604249
 * @since 2023-12-24 16:19
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapperExtend<SysRoleMenu> {

    /**
     * 根据角色ID查询菜单列表
     *
     * @param roleId
     * @return
     */
    default List<SysRoleMenu> selectByRoleId(Long roleId) {
        return selectList(SysRoleMenu::getRoleId, roleId);
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
}
