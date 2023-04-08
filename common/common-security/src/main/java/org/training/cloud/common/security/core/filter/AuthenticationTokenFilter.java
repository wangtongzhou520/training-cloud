package org.training.cloud.common.security.core.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * token请求过滤
 *
 * @author wangtongzhou
 * @since 2023-03-25 10:58
 */
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {

//    @Autowired
//    private SecurityProperties securityProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

//        //从请求头获取
//        String token = SecurityUtils.getAuthorization(request, securityProperties.getTokenHeader());
//        if(StringUtils.isNotBlank(token)){
//            //检验token合法性
//
//            //构建用户信息
//        }


        chain.doFilter(request, response);
    }


}
