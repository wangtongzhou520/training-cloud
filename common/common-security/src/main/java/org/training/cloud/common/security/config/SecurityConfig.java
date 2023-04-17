package org.training.cloud.common.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.training.cloud.common.security.core.SecuritySecurityCheckService;
import org.training.cloud.common.security.core.filter.JwtAuthenticationTokenFilter;
import org.training.cloud.common.security.handler.CustomizeAccessDeniedHandler;
import org.training.cloud.common.security.handler.CustomizeAuthExceptionEntryPoint;

/**
 * 安全配置
 *
 * @author wangtongzhou
 * @since 2023-03-03 21:27
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    @Autowired
    private NotAuthenticationConfig notAuthenticationConfig;

    @Autowired
    private CustomizeAccessDeniedHandler customizeAccessDeniedHandler;

    @Autowired
    private CustomizeAuthExceptionEntryPoint customizeAuthExceptionEntryPoint;


    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //支持跨域
        http.cors().and()
                //csrf关闭
                .csrf().disable()
                //不使用session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests(rep -> rep.antMatchers(notAuthenticationConfig.getPermitAllUrls().toArray(new String[0]))
                        .permitAll().anyRequest().authenticated())
                .exceptionHandling()
                //异常认证
                .authenticationEntryPoint(customizeAuthExceptionEntryPoint)
                .accessDeniedHandler(customizeAccessDeniedHandler)
                .and()
                //token校验
                .addFilterBefore(new JwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
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


}
