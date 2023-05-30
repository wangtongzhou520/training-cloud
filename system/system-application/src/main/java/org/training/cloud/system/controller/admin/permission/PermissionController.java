package org.training.cloud.system.controller.admin.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.dto.permission.AddUserRoleDTO;
import org.training.cloud.system.service.permission.PermissionService;

import javax.annotation.Resource;

/**
 * 权限相关接口
 *
 * @author wangtongzhou 18635604249
 * @since 2023-05-30 21:18
 */
@RestController
@RequestMapping("/sys/admin/")
@Tag(name = "权限相关接口")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @PostMapping("/addUserRole")
    @Operation(summary = "添加用户角色信息")
    public CommonResponse<Boolean> addUserRole(@RequestBody @Validated AddUserRoleDTO addUserRoleDTO) {
        permissionService.addUserRole(addUserRoleDTO.getUserId(), addUserRoleDTO.getRoleIds());
        return CommonResponse.ok();
    }

}
