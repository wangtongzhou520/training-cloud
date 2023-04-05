package org.training.cloud.common.security.core.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.training.cloud.common.security.core.model.AuthUser;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

/**
 * 安全框架工具类
 *
 * @author wangtongzhou 
 * @since 2023-03-29 21:44
 */
public class SecurityUtils {

    public static final String AUTHORIZATION_BEARER = "Bearer";

    private SecurityUtils(){}

    public static String obtainAuthorization(HttpServletRequest request, String header) {
        String authorization = request.getHeader(header);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
        if (index == -1) {
            return null;
        }
        return authorization.substring(index + 7).trim();
    }


    /**
     * 获得当前认证信息
     *
     * @return 认证信息
     */
    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        return context.getAuthentication();
    }


    /**
     * 设置当前用户
     *
     * @param authUser
     * @param request
     */
    public static void setLoginUser(AuthUser authUser, HttpServletRequest request) {
        //创建Authentication，并设置到上下文
        Authentication authentication = buildAuthentication(authUser, request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static Authentication buildAuthentication(AuthUser authUser, HttpServletRequest request) {
        //创建UsernamePasswordAuthenticationToken对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authUser, null, Collections.emptyList());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return authenticationToken;
    }
}
