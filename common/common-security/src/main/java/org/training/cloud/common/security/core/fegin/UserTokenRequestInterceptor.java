package org.training.cloud.common.security.core.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * 处理UserToken
 *
 * @author wangtongzhou 
 * @since 2023-04-08 21:34
 */
public class UserTokenRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.body();
    }
}
