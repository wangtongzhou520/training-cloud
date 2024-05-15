package org.training.cloud.common.security.core.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.training.cloud.common.feign.core.FeignUtils;
import org.training.cloud.common.security.core.model.AuthUser;
import org.training.cloud.common.security.core.utils.SecurityUtils;

import static org.training.cloud.common.security.core.utils.SecurityUtils.USER_INFO;

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
        if (user != null) {
            FeignUtils.createJsonHeader(requestTemplate, USER_INFO, user);
        }
    }
}
