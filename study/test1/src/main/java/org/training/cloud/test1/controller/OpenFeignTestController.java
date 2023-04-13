package org.training.cloud.test1.controller;

import org.training.cloud.common.web.core.vo.CommonResponse;
import org.training.cloud.system.api.oauth2.Oauth2TokenApi;
import org.training.cloud.system.vo.dept.DeptTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.system.vo.oauth2.OAuth2AccessTokenVO;

import java.util.List;

/**
 * feign test
 *
 * @author wangtongzhou
 * @since 2020-12-18 13:40
 */
@RestController
public class OpenFeignTestController {

    @Autowired
    private Oauth2TokenApi oauth2TokenApi;

    @GetMapping("/test")
    public CommonResponse<OAuth2AccessTokenVO> test() {
        CommonResponse<OAuth2AccessTokenVO> auth2AccessTokenVOCommonResponse =
                oauth2TokenApi.checkAccessToken("123456");
        return auth2AccessTokenVOCommonResponse;
    }
}
