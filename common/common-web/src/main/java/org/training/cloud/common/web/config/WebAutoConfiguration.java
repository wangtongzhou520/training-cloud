package org.training.cloud.common.web.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.training.cloud.common.web.handler.GlobalExceptionHandler;

import javax.servlet.Filter;

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
