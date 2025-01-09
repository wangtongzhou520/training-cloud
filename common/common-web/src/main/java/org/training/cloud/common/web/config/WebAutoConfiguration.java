package org.training.cloud.common.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.training.cloud.common.web.handler.GlobalExceptionHandler;

/**
 * @author wangtongzhou
 * @since 2023-04-05 16:34
 */
@AutoConfiguration
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }
}
