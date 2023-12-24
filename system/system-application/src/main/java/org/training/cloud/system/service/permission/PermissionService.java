package org.training.cloud.system.service.permission;

import java.util.List;
import java.util.Set;

/**
 * 用户角色信息
 *
 * @author wangtongzhou 18635604249
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
     * 为菜单赋权
     *
     * @param roleId
     * @param menuIds
     */
    void addRoleMenu(Long roleId,List<Long> menuIds);


}
