package org.training.cloud.system.controller.admin.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.permission.RoleConvert;
import org.training.cloud.system.dto.permission.AddRoleDTO;
import org.training.cloud.system.dto.permission.ModifyRoleDTO;
import org.training.cloud.system.dto.permission.RoleDTO;
import org.training.cloud.system.entity.permission.SysRole;
import org.training.cloud.system.service.permission.RoleService;
import org.training.cloud.system.vo.permission.RoleVO;

import java.util.List;

/**
 * 角色
 *
 * @author wangtongzhou
 * @since 2020-11-09 14:33
 */
@RestController
@RequestMapping("/sys")
@Tag(name = "角色信息")
public class RoleController {


    @Autowired
    private RoleService roleService;


    @PostMapping("/role")
    @Operation(summary = "添加角色信息")
    public CommonResponse<?> addRole(@RequestBody @Validated AddRoleDTO addRoleDTO) {
        roleService.addRole(addRoleDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/role")
    @Operation(summary = "修改角色信息")
    public CommonResponse<?> modifyUser(@RequestBody @Validated ModifyRoleDTO modifyRoleDTO) {
        roleService.modifyRole(modifyRoleDTO);
        return CommonResponse.ok();
    }


    @PostMapping("/role/page")
    @Operation(summary = "分页查询角色信息")
    public CommonResponse<PageResponse<RoleVO>> pageRoles(@RequestBody RoleDTO roleDTO) {
        PageResponse<SysRole> pageResponse = roleService.pageRole(roleDTO);
        return CommonResponse.ok(RoleConvert.INSTANCE.convert(pageResponse));
    }

    @GetMapping("/role/all")
    @Operation(summary = "获取所有的角色信息")
    public CommonResponse<List<RoleVO>> allRoles() {
        List<SysRole> allRoles = roleService.allRoles();
        return CommonResponse.ok(RoleConvert.INSTANCE.convert(allRoles));
    }


    /**
     * 删除角色
     *
     * @param id
     * @return
     */
    @DeleteMapping("/role/{id}")
    @Operation(summary = "删除角色")
    public CommonResponse<?> delRole(@PathVariable("id") Long id) {
        roleService.removeByRoleId(id);
        return CommonResponse.ok();
    }


}
