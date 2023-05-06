package org.training.cloud.common.feign.core.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * feignConfig配置
 *
 * @author wangtongzhou 
 * @since 2023-04-13 20:17
 */
@Configuration
public class FeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;



        
    }
}
