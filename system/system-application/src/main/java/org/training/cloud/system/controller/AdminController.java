package org.training.cloud.system.controller;

import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.dto.admin.AdminLoginDTO;
import org.training.cloud.system.vo.admin.AdminAccessTokenVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 管理员登录
 *
 * @author wangtongzhou
 * @since 2020-11-09 16:52
 */
@RestController
@RequestMapping("/sys")
@Api("管理员登录")
public class AdminController {

    /**
     * 登录
     *
     * @param adminLoginDTO adminLoginDTO
     * @return true or false
     */
    @PostMapping("/login")
    @ApiOperation("管理登录")
    public CommonResponse<AdminAccessTokenVO> login(@RequestBody @Validated AdminLoginDTO adminLoginDTO) {

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
