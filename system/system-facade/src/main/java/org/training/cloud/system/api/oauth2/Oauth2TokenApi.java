package org.training.cloud.system.api.oauth2;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    /**
     * 检查 token
     */
    @SuppressWarnings("HttpUrlsUsage")
    String CHECK_TOKEN = "http://" + ApiConstants.NAME + "/sys/oauth2token/checkAccessToken";


    @GetMapping("/sys/oauth2/token/checkAccessToken/{accessToken}")
    @Operation(summary = "校验访问令牌")
    CommonResponse<Oauth2AccessTokenVO> checkAccessToken(@PathVariable("accessToken") String accessToken);

}
