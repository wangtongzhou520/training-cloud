package org.training.cloud.system.service.permission;

import java.util.List;
import java.util.Set;

/**
 * 用户角色信息
 *
 * @author wangtongzhou
 * @since 2023-05-28 18:58
 */
public interface PermissionService {

    /**
     * 添加用户角色信息
     *
     * @param userId
     * @param roleIds
     */
    void addUserRole(Long userId, List<Long> roleIds);

    /**
     * 删除用户的角色信息
     *
     * @param userId
     */
    void removeUserRole(Long userId);

    /**
     * 获取用户所有的角色信息
     *
     * @param userId
     * @return
     */
    Set<Long> getRoleIdListByUserId(Long userId);


    /**
     * 根据角色ID查看具有权限信息
     *
     * @param roleId
     * @return
     */
    Set<Long> getMenuIdListByRoleId(Long roleId);


    /**
     * 根据多个角色信息获取所有的权限信息
     *
     * @param roleIds
     * @return
     */
    Set<Long> getMenuIdListByRoleIds(Set<Long> roleIds);



    /**
     * 为菜单赋权
     *
     * @param roleId
     * @param menuIds
     */
    void addRoleMenu(Long roleId,List<Long> menuIds);

    /**
     * 删除菜单关联的角色信息
     *
     * @param menuId
     */
    void removeListByMenuId(Long menuId);

    /**
     * 删除角色和菜单信息
     *
     * @param roleId
     */
    void removeListByRoleId(Long roleId);


}
