package org.training.cloud.system.controller.admin.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.security.core.annotations.NotAuthentication;
import org.training.cloud.common.web.utils.WebUtil;
import org.training.cloud.system.dto.auth.AuthLoginDTO;
import org.training.cloud.system.dto.auth.AuthPermissionVO;
import org.training.cloud.system.service.auth.AuthService;
import org.training.cloud.system.vo.auth.AuthLoginVO;

import javax.validation.Valid;

/**
 * 管理员登录
 *
 * @author wangtongzhou
 * @since 2020-11-09 16:52
 */
@RestController
@RequestMapping("/sys/auth")
@Tag(name = "管理员登录")
public class AuthController {

    @Autowired
    private AuthService service;

    /**
     * 登录
     *
     * @param authLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "账号密码登录")
    @NotAuthentication
    public CommonResponse<AuthLoginVO> login(@RequestBody @Valid AuthLoginDTO authLoginDTO) {
        return CommonResponse.ok(service.login(authLoginDTO));
    }

    @GetMapping("/getUserPermission")
    @Operation(summary = "获取用户的权限信息")
    @PreAuthorize("@ssc.hasPermission('system:permission:list')")
    public CommonResponse<AuthPermissionVO> getUserPermission() {
        Long userId = WebUtil.getLoginUserId();
        if (userId == null) {
            return null;
        }
        return CommonResponse.ok(service.getUserPermission(userId));
    }

    /**
     * 登出
     *
     * @param userName 用户名
     * @return
     */
    @PostMapping("/users")
    @Operation(summary = "登出")
    @NotAuthentication
    public CommonResponse<?> loginOut(String userName) {
        return CommonResponse.ok();
    }
}
