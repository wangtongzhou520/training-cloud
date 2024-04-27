package org.training.cloud.system.controller.admin.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.dto.permission.AddRoleMenuDTO;
import org.training.cloud.system.dto.permission.AddUserRoleDTO;
import org.training.cloud.system.service.permission.PermissionService;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Set;

/**
 * 权限相关接口
 *
 * @author wangtongzhou
 * @since 2023-05-30 21:18
 */
@RestController
@RequestMapping("/sys/permission")
@Tag(name = "权限相关接口")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("/addUserRole")
    @Operation(summary = "添加用户角色信息")
    @PreAuthorize("@ss.hasPermission('system:permission:role')")
    public CommonResponse<Boolean> addUserRole(@RequestBody @Valid AddUserRoleDTO addUserRoleDTO) {
        permissionService.addUserRole(addUserRoleDTO.getUserId(), addUserRoleDTO.getRoleIds());
        return CommonResponse.ok();
    }

    @GetMapping("/listRoles")
    @Parameter(name = "userId", description = "用户编号", required = true)
    @Operation(summary = "根据用户ID获取用户所具有的角色信息")
    @PreAuthorize("@ss.hasPermission('system:permission:list')")
    public CommonResponse<Set<Long>> listRoles(@RequestParam("userId") Long userId){
        return CommonResponse.ok(permissionService.getRoleIdListByUserId(userId));
    }

    @GetMapping("/listMenus")
    @Parameter(name = "roleId", description = "角色编号", required = true)
    @Operation(summary = "根据角色ID获取用户所具有权限信息")
    @PreAuthorize("@ss.hasPermission('system:permission:list')")
    public CommonResponse<Set<Long>> listMenus(@RequestParam("roleId") Long roleId){
        return CommonResponse.ok(permissionService.getMenuIdListByRoleId(roleId));
    }


    @PostMapping("/addRoleMenu")
    @Operation(summary = "添加角色权限信息")
    @PreAuthorize("@ss.hasPermission('system:permission:menu')")
    public CommonResponse<Boolean> addRoleMenu(@RequestBody @Valid AddRoleMenuDTO addRoleMenuDTO) {
        permissionService.addRoleMenu(addRoleMenuDTO.getRoleId(),
                addRoleMenuDTO.getMenuIds());
        return CommonResponse.ok();
    }

}
