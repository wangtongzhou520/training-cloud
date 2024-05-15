package org.training.cloud.system.api.oauth2;

import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.service.oauth2.Oauth2TokenService;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

import javax.annotation.Resource;

/**
 * token实现
 *
 * @author wangtongzhou
 * @since 2024-05-15 07:55
 */
@RestController
public class Oauth2TokenImplApi implements Oauth2TokenApi {
    @Resource
    private Oauth2TokenService oauth2TokenService;

    @Override
    public CommonResponse<Oauth2AccessTokenVO> checkAccessToken(String accessToken) {
        return CommonResponse.ok(oauth2TokenService.checkAccessToken(accessToken));
    }
}
