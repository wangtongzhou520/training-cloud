package org.training.cloud.system.controller.admin.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.user.UserConvert;
import org.training.cloud.system.dto.user.AddUserDTO;
import org.training.cloud.system.dto.user.ModifyUserDTO;
import org.training.cloud.system.dto.user.UserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.service.user.UserService;
import org.training.cloud.system.vo.user.SysUserVO;

/**
 * 用户相关接口
 *
 * @author wangtongzhou
 * @since 2020-08-13 18:49
 */
@RestController
@RequestMapping("/sys/admin")
@Tag(name = "管理员相关信息")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 保存用户信息
     *
     * @param addUserDTO saveUserDTO
     * @return true or false
     */
    @PostMapping("/user")
    @Operation(summary = "添加用户信息")
    public CommonResponse<?> saveUser(@RequestBody @Validated AddUserDTO addUserDTO) {
        userService.addUser(addUserDTO);
        return CommonResponse.ok();
    }

    /**
     * 更新用户信息
     *
     * @param modifyUserDTO
     * @return
     */
    @PutMapping("/user")
    @Operation(summary = "添加用户信息")
    public CommonResponse<?> updateUser(@RequestBody @Validated ModifyUserDTO modifyUserDTO) {
        userService.updateUser(modifyUserDTO);
        return CommonResponse.ok();
    }


    /**
     * 分页查询用户信息
     *
     * @return 分页用户信息
     */
    @GetMapping("/page")
    @Operation(summary = "分页查询管理端用户信息")
    public CommonResponse<PageResponse<SysUserVO>> pageAdminUser(@RequestBody UserDTO userDTO) {
        PageResponse<SysUser> pageResponse = userService.pageAdminUser(userDTO);
        return CommonResponse.ok(UserConvert.INSTANCE.convert(pageResponse));
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
        userService.removeUserById(id);
        return CommonResponse.ok();
    }
}
