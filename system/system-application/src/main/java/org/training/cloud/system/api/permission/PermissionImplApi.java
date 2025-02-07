package org.training.cloud.system.api.permission;

import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.service.permission.PermissionService;

import javax.annotation.Resource;

/**
 * 权限相关
 *
 * @author wangtongzhou
 * @since 2024-05-15 06:53
 */
@RestController
public class PermissionImplApi implements PermissionApi {

    @Resource
    private PermissionService permissionService;

    @Override
    public CommonResponse<Boolean> hasAnyPermissions(Long userId, String... permissions) {
        return CommonResponse.ok(permissionService.hasAnyPermissions(userId, permissions));
    }

    @Override
    public CommonResponse<Boolean> hasAnyRoles(Long userId, String... roles) {
        return CommonResponse.ok(permissionService.hasAnyRoles(userId, roles));
    }
}
