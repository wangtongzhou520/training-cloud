package org.training.cloud.system.controller.oauth2;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.security.core.annotations.NotAuthentication;
import org.training.cloud.common.web.core.vo.CommonResponse;
import org.training.cloud.system.service.oauth2.OAuth2TokenService;
import org.training.cloud.system.vo.oauth2.OAuth2AccessTokenVO;

/**
 * token认证
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-11 20:19
 */
@Tag(name = "Oauth2 Token 管理")
@RestController
@RequestMapping("/sys/oauth2token")
public class Oauth2TokenController {

    @Autowired
    private OAuth2TokenService auth2TokenService;

    @NotAuthentication
    @GetMapping("/checkAccessToken/{accessToken}")
    public CommonResponse<OAuth2AccessTokenVO> checkAccessToken(@PathVariable(
            "accessToken") String accessToken) {
        return CommonResponse.ok(auth2TokenService.checkAccessToken(accessToken));
    }
}