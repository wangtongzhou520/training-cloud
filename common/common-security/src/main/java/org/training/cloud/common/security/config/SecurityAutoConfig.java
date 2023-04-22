package org.training.cloud.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.training.cloud.common.security.core.filter.AuthenticationTokenFilter;
import org.training.cloud.common.security.core.service.SecuritySecurityCheckService;
import org.training.cloud.common.web.handler.GlobalExceptionHandler;
import org.training.cloud.system.api.oauth2.Oauth2TokenApi;

/**
 * 自动加载类配置文件
 *
 * @author wangtongzhou 
 * @since 2023-04-09 18:27
 */
@AutoConfiguration
@EnableConfigurationProperties(NotAuthenticationProperties.class)
public class SecurityAutoConfig {

    @Autowired
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
    public SecuritySecurityCheckService permissionService() {
        return new SecuritySecurityCheckService();
    }

    @Bean
    public AuthenticationTokenFilter authenticationTokenFilter(GlobalExceptionHandler exceptionHandler,
                                                               NotAuthenticationProperties authenticationProperties,
                                                               Oauth2TokenApi oauth2TokenApi) {
        return new AuthenticationTokenFilter(authenticationProperties, oauth2TokenApi, exceptionHandler);
    }
}
