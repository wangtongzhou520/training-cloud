package org.training.cloud.common.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.training.cloud.common.security.core.fegin.UserTokenRequestInterceptor;
import org.training.cloud.system.api.oauth2.Oauth2TokenApi;

/**
 * FeignClient自动加载
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-09 18:44
 */
@AutoConfiguration
@EnableFeignClients(clients = {Oauth2TokenApi.class})
public class FeignClientAutoConfig {
    @Bean
    public UserTokenRequestInterceptor userTokenRequestInterceptor() {
        return new UserTokenRequestInterceptor();
    }
}
