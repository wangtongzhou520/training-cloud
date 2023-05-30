package org.training.cloud.system.service.permission;

import java.util.List;

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

}
