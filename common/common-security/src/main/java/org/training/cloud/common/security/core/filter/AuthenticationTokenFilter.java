package org.training.cloud.common.security.core.filter;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.training.cloud.common.security.config.NotAuthenticationProperties;
import org.training.cloud.common.security.core.model.AuthUser;
import org.training.cloud.common.security.core.utils.SecurityUtils;
import org.training.cloud.common.core.utils.josn.JsonUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.web.handler.GlobalExceptionHandler;
import org.training.cloud.system.api.oauth2.Oauth2TokenApi;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * token请求过滤
 *
 * @author wangtongzhou
 * @since 2023-03-25 10:58
 */
@RequiredArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private final NotAuthenticationProperties notAuthenticationProperties;

    private final Oauth2TokenApi oauth2TokenApi;

    private final GlobalExceptionHandler globalExceptionHandler;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        //分为两种情况
        //1. 网关或者feign等调用
        AuthUser authUser = getHeaderAuthUser(request);

        //2. 根据认证的信息获取
        if (Objects.isNull(authUser)) {
            String token = SecurityUtils.getAuthorization(request, notAuthenticationProperties.getTokenHeader());
            if (StringUtils.isNotBlank(token)) {
                try {
                    authUser = buildAuthUserByToken(token);
                } catch (Throwable ex) {
                    CommonResponse<?> result = globalExceptionHandler.allExceptionHandler(request, ex);
                    //处理编码方式，防止中文乱码的情况
                    response.setContentType("text/json;charset=utf-8");
                    //塞到HttpServletResponse中返回给前台
                    response.getWriter().write(JsonUtils.toJsonString(result));
                    return;
                }
            }
        }
        if (Objects.nonNull(authUser)) {
            SecurityUtils.setLoginUser(authUser, request);
        }
        chain.doFilter(request, response);
    }

    private AuthUser buildAuthUserByToken(String token) {
        //检验token合法性
        Oauth2AccessTokenVO oAuth2AccessTokenVO =
                oauth2TokenApi.checkAccessToken(token).getApiData();
        if (Objects.isNull(oAuth2AccessTokenVO)) {
            return null;
        }
        //构建用户信息
        return new AuthUser().setId(oAuth2AccessTokenVO.getUserId())
                .setUserType(oAuth2AccessTokenVO.getUserType())
                .setScopes(oAuth2AccessTokenVO.getScopes());
    }

    private AuthUser getHeaderAuthUser(HttpServletRequest request) {
        String userToken = request.getHeader(SecurityUtils.USER_TOKE);
        return StringUtils.isNotBlank(userToken) ?
                JsonUtils.parseObject(userToken, AuthUser.class) : null;
    }


}
