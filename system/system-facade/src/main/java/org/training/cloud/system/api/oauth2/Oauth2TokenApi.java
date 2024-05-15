package org.training.cloud.system.api.oauth2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.constant.ApiConstants;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

/**
 * OAuth2 Token
 *
 * @author wangtongzhou
 * @since 2023-04-09 14:23
 */
@FeignClient(value = ApiConstants.NAME)
public interface Oauth2TokenApi {


    String PREFIX = ApiConstants.PREFIX + "/oauth2";


    /**
     * 检查 token
     */
    String CHECK_TOKEN = "http://" + ApiConstants.NAME + PREFIX + "/checkAccessToken";


    @GetMapping(PREFIX + "/checkAccessToken")
    @Operation(summary = "校验访问令牌")
    @Parameter(name = "accessToken", description = "访问令牌", required = true, example = "123456")
    CommonResponse<Oauth2AccessTokenVO> checkAccessToken(@RequestParam("accessToken") String accessToken);

}
