package org.training.cloud.common.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.training.cloud.common.security.core.filter.AuthenticationTokenFilter;
import org.training.cloud.common.security.core.handler.CustomizeAccessDeniedHandler;
import org.training.cloud.common.security.core.handler.CustomizeAuthExceptionEntryPoint;
import org.training.cloud.common.security.core.service.SecuritySecurityCheckService;
import org.training.cloud.common.web.handler.GlobalExceptionHandler;
import org.training.cloud.system.api.oauth2.Oauth2TokenApi;
import org.training.cloud.system.api.permission.PermissionApi;

import javax.annotation.Resource;

/**
 * 自动加载类配置文件
 *
 * @author wangtongzhou
 * @since 2023-04-09 18:27
 */
@AutoConfiguration
@EnableConfigurationProperties(NotAuthenticationProperties.class)
public class SecurityAutoConfig {

    @Resource
    private NotAuthenticationProperties notAuthenticationProperties;


    /**
     * 密码
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean("ssc")
    public SecuritySecurityCheckService permissionService(PermissionApi permissionApi) {
        return new SecuritySecurityCheckService(permissionApi);
    }

    /**
     * 权限不够
     *
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomizeAccessDeniedHandler();
    }


    /**
     * 认证失败
     *
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomizeAuthExceptionEntryPoint();
    }


    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter(GlobalExceptionHandler exceptionHandler,
                                                               NotAuthenticationProperties authenticationProperties,
                                                               Oauth2TokenApi oauth2TokenApi) {
        return new AuthenticationTokenFilter(authenticationProperties, oauth2TokenApi, exceptionHandler);
    }




}
