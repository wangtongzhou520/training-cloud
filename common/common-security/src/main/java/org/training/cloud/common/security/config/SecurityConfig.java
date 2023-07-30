package org.training.cloud.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.training.cloud.common.security.core.filter.AuthenticationTokenFilter;
import org.training.cloud.common.security.core.handler.CustomizeAccessDeniedHandler;
import org.training.cloud.common.security.core.handler.CustomizeAuthExceptionEntryPoint;
import org.training.cloud.common.security.core.service.SecuritySecurityCheckService;

/**
 * 安全配置
 *
 * @author wangtongzhou
 * @since 2023-03-03 21:27
 */
@AutoConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


    @Autowired
    private NotAuthenticationConfig notAuthenticationConfig;

    @Autowired
    private AuthenticationTokenFilter authenticationTokenFilter;


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        //支持跨域
        http.cors().and()
                //csrf关闭
                .csrf().disable()
                //不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .headers().frameOptions().disable().and()
                .authorizeRequests(rep -> rep.antMatchers(notAuthenticationConfig.getPermitAllUrls().toArray(new String[0]))
                        .permitAll().anyRequest().authenticated())
                .exceptionHandling()
                //异常认证
                .authenticationEntryPoint(new CustomizeAuthExceptionEntryPoint())
                .accessDeniedHandler(new CustomizeAccessDeniedHandler())
                .and().addFilterBefore(authenticationTokenFilter,
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    /**
     * 获取AuthenticationManager
     *
     * @param configuration
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }





}
