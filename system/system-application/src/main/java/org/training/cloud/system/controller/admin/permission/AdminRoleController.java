package org.training.cloud.system.controller.admin.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.permission.RoleConvert;
import org.training.cloud.system.convert.user.UserConvert;
import org.training.cloud.system.dto.permission.AddRoleDTO;
import org.training.cloud.system.dto.permission.ModifyRoleDTO;
import org.training.cloud.system.dto.permission.RoleDTO;
import org.training.cloud.system.dto.user.UserDTO;
import org.training.cloud.system.entity.role.SysRole;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.service.permission.RoleService;
import org.training.cloud.system.vo.permission.RoleVO;
import org.training.cloud.system.vo.user.SysUserVO;

/**
 * 角色
 *
 * @author wangtongzhou
 * @since 2020-11-09 14:33
 */
@RestController
@RequestMapping("/sys/admin/")
@Tag(name = "角色信息")
public class AdminRoleController {


    @Autowired
    private RoleService roleService;


    @PostMapping("/role")
    @Operation(summary = "添加角色信息")
    public CommonResponse<?> addRole(@RequestBody @Validated AddRoleDTO addRoleDTO) {
        roleService.addRole(addRoleDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/user")
    @Operation(summary = "修改角色信息")
    public CommonResponse<?> modifyUser(@RequestBody @Validated ModifyRoleDTO modifyRoleDTO) {
        roleService.modifyRole(modifyRoleDTO);
        return CommonResponse.ok();
    }


    @GetMapping("/page")
    @Operation(summary = "分页查询角色信息")
    public CommonResponse<PageResponse<RoleVO>> pageAdminUser(@RequestBody RoleDTO roleDTO) {
        PageResponse<SysRole> pageResponse = roleService.pageRole(roleDTO);
        return CommonResponse.ok(RoleConvert.INSTANCE.convert(pageResponse));
    }


    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping("/user")
    @Operation(summary = "删除部门")
    public CommonResponse<?> delDept(@RequestParam Long id) {
//        userService.removeUserById(id);
        return CommonResponse.ok();
    }



}
