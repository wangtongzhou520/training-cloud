package org.training.cloud.common.security.core.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PatternMatchUtils;
import org.training.cloud.system.api.permission.PermissionApi;

import java.util.Arrays;
import java.util.Collection;

import static org.training.cloud.common.web.utils.WebUtil.getLoginUserId;

/**
 * SecuritySecurity 检查有没权限
 *
 * @author wangtongzhou
 * @since 2023-03-19 14:22
 */
@RequiredArgsConstructor
public class SecuritySecurityCheckService {


    private final PermissionApi permissionApi;


    public boolean hasPermission(String permission) {
        return hasAnyPermissions(permission);
    }

    public boolean hasAnyPermissions(String... permissions) {
        return permissionApi.hasAnyPermissions(getLoginUserId(), permissions).getData();
    }

    public boolean hasRole(String role) {
        return hasAnyRoles(role);
    }

    public boolean hasAnyRoles(String... roles) {
        return permissionApi.hasAnyRoles(getLoginUserId(), roles).getData();
    }
}
