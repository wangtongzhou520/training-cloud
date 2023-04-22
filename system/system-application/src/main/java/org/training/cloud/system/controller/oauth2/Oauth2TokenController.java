package org.training.cloud.system.controller.oauth2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.oauth2.Oauth2AccessTokenDTO;
import org.training.cloud.system.service.oauth2.Oauth2TokenService;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

/**
 * token认证
 *
 * @author wangtongzhou 
 * @since 2023-04-11 20:19
 */
@Tag(name = "Oauth2 Token管理")
@RestController
@RequestMapping("/sys/oauth2/token")
public class Oauth2TokenController {

    @Autowired
    private Oauth2TokenService auth2TokenService;

    @GetMapping("/checkAccessToken/{accessToken}")
    @Operation(summary = "检验token合法性")
    public CommonResponse<Oauth2AccessTokenVO> checkAccessToken(@PathVariable("accessToken") String accessToken) {
        return CommonResponse.ok(auth2TokenService.checkAccessToken(accessToken));
    }


    @GetMapping("/refresh")
    @Operation(summary = "根据刷新令牌获取新token信息")
    public CommonResponse<Oauth2AccessTokenVO> refreshAccessToken(@RequestParam("refreshToken") String refreshToken, @RequestParam("clientId") String clientId) {
        return CommonResponse.ok(auth2TokenService.refreshAccessToken(refreshToken, clientId));
    }


    @PostMapping("/page")
    @Operation(summary = "分页获取token信息")
    public CommonResponse<PageResponse<Oauth2AccessTokenVO>> pageAccessToken(@RequestBody Oauth2AccessTokenDTO accessTokenDTO) {
        return CommonResponse.ok(auth2TokenService.pageOauth2AccessToken(accessTokenDTO));
    }


    @DeleteMapping("/delete")
    @Operation(summary = "检验token合法性")
    public CommonResponse deleteAccessToken(@RequestParam("accessToken") String accessToken) {
        auth2TokenService.removeToken(accessToken);
        return CommonResponse.ok();
    }


}
