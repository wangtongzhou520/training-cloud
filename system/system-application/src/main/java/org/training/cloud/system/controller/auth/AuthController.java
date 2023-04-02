package org.training.cloud.system.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.dto.auth.AuthLoginDTO;
import org.training.cloud.system.vo.auth.AuthLoginVO;

/**
 * 管理员登录
 *
 * @author wangtongzhou
 * @since 2020-11-09 16:52
 */
@RestController
@RequestMapping("/sys")
@Api("管理员登录")
public class AuthController {

    /**
     * 登录
     *
     * @param authLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("账号密码登录")
    public CommonResponse<AuthLoginVO> login(@RequestBody @Validated AuthLoginDTO authLoginDTO) {

        return null;
    }

    /**
     * 登出
     *
     * @param userName 用户名
     * @return
     */
    @PostMapping("/users")
    @ApiOperation("登出")
    public CommonResponse<?> loginOut(String userName) {
        return CommonResponse.ok();
    }
}
