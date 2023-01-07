package com.springcloud.study.test1.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * feign config
 *
 * @author wangtongzhou
 * @since 2020-12-18 21:48
 */
public class FeignConfig {

    @Bean
    public Logger.Level level() {
        return Logger.Level.BASIC;
    }
}
