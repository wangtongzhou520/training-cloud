package org.training.cloud.getway.filter.security;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import org.training.cloud.common.core.utils.josn.JsonUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.getway.utils.AuthUser;
import org.training.cloud.getway.utils.SignUtils;
import org.training.cloud.system.api.oauth2.Oauth2TokenApi;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Function;

/**
 * 签名检查
 *
 * @author wangtongzhou
 * @since 2023-04-26 07:13
 */
@Component
public class AuthenticationTokenFilter implements GlobalFilter, Ordered {


    private static final TypeReference<CommonResponse<Oauth2AccessTokenVO>> CHECK_RESULT_TYPE_REFERENCE
            = new TypeReference<CommonResponse<Oauth2AccessTokenVO>>() {
    };




    private final WebClient webClient;


    public AuthenticationTokenFilter(ReactorLoadBalancerExchangeFilterFunction lbFunction) {
        this.webClient = WebClient.builder()
                .filter(lbFunction).build();
    }


    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, GatewayFilterChain chain) {
        //首先移除签名 防止登录被伪造
        SignUtils.removeUserInfo(exchange);

        //从请求中获取token
        String token = SignUtils.getAuthorization(exchange);
        //如果token为空那么可能是登录
        if (StringUtils.isBlank(token)) {
            return chain.filter(exchange);
        }

        return getLoginUser(token).flatMap(user -> {
            //如果为空 说明无用户信息
            if (Objects.isNull(user)) {
                return chain.filter(exchange);
            }
            //设置请求头
            ServerWebExchange newExchange = exchange.mutate()
                    .request(builder -> SignUtils.setUserInfo(builder, user)).build();
            return chain.filter(newExchange);
        });

    }


    private Mono<AuthUser> getLoginUser(String token) {
        return checkAccessToken(token).flatMap((Function<String, Mono<AuthUser>>) body -> {
            AuthUser authUser = buildAuthUserByToken(body);
            if (Objects.nonNull(authUser)) {
                return Mono.just(authUser);
            }
            return Mono.empty();
        });
    }


    private Mono<String> checkAccessToken(String token) {
        return webClient.get()
                .uri(Oauth2TokenApi.CHECK_TOKEN, uriBuilder -> uriBuilder.queryParam("accessToken", token).build())
                .retrieve().bodyToMono(String.class);
    }


    private AuthUser buildAuthUserByToken(String body) {
        //检验token合法性
        CommonResponse<Oauth2AccessTokenVO> result = JsonUtils.parseObject(body, CHECK_RESULT_TYPE_REFERENCE);
        if (Objects.isNull(result)) {
            return null;
        }
        if (!result.isSuccess()) {
            return null;
        }
        //构建用户信息
        Oauth2AccessTokenVO oauth2AccessTokenVO = result.getData();
        return new AuthUser().setId(oauth2AccessTokenVO.getUserId())
                .setUserType(oauth2AccessTokenVO.getUserType())
                .setScopes(oauth2AccessTokenVO.getScopes());
    }

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
