package org.training.cloud.system.controller.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.security.core.annotations.NotAuthentication;
import org.training.cloud.system.dto.auth.AuthLoginDTO;
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
@RequestMapping("/sys/admin")
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

    /**
     * 登出
     *
     * @param userName 用户名
     * @return
     */
    @PostMapping("/users")
    @Operation(summary = "登出")
    public CommonResponse<?> loginOut(String userName) {
        return CommonResponse.ok();
    }
}
