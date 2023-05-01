package org.training.cloud.getway.utils;


import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.training.cloud.common.core.utils.josn.JsonUtils;

/**
 * 签名校验的工具类
 *
 * @author wangtongzhou
 * @since 2023-03-29 21:44
 */
public class SignUtils {

    public static final String AUTHORIZATION_BEARER = "Bearer";

    /**
     * 此标识网关或者feign传过来
     */
    public static final String USER_INFO = "user-info";


    public static String getAuthorization(ServerWebExchange exchange) {
        String authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION_BEARER);
        if (!StringUtils.hasText(authorization)) {
            return null;
        }
        int index = authorization.indexOf(AUTHORIZATION_BEARER + " ");
        if (index == -1) {
            return null;
        }
        return authorization.substring(index + 7).trim();
    }


    public static ServerWebExchange removeUserInfo(ServerWebExchange exchange) {
        // 如果不包含，直接返回
        if (!exchange.getRequest().getHeaders().containsKey(USER_INFO)) {
            return exchange;
        }
        ServerHttpRequest request = exchange.getRequest().mutate()
                .headers(httpHeaders -> httpHeaders.remove(USER_INFO)).build();
        return exchange.mutate().request(request).build();
    }


    public static void setUserInfo(ServerHttpRequest.Builder builder,
                              AuthUser user) {
        builder.header(USER_INFO, JsonUtils.toJsonString(user));
    }
}
