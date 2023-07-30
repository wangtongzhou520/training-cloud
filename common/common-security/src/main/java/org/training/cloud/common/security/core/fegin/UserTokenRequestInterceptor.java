package org.training.cloud.common.security.core.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.training.cloud.common.security.core.model.AuthUser;
import org.training.cloud.common.security.core.utils.SecurityUtils;

/**
 * 处理UserToken
 *
 * @author wangtongzhou 
 * @since 2023-04-08 21:34
 */
public class UserTokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        AuthUser user = SecurityUtils.getAuthUser();
//        if (user != null) {
//            FeignUtils.createJsonHeader(requestTemplate, SecurityUtils.USER_INFO, user);
//        }
    }
}
