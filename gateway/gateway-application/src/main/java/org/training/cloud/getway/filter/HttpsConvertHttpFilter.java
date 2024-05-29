package org.training.cloud.getway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.server.ServerWebExchange;
import org.training.cloud.common.core.constant.UserExceptionCode;
import org.training.cloud.common.core.exception.ServerException;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 *
 *
 * @author wangtongzhou
 * @since 2024-05-30 06:47
 */
@Component
@Slf4j
public class HttpsConvertHttpFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI originalUri = request.getURI();
        ServerHttpRequest.Builder mutate = request.mutate();
        String forwardUri = request.getURI().toString();

        if (forwardUri != null && forwardUri.startsWith("https")) {
            try {
                URI mutatedUri = new URI("http",
                        originalUri.getUserInfo(),
                        originalUri.getHost(),
                        originalUri.getPort(),
                        originalUri.getPath(),
                        originalUri.getQuery(),
                        originalUri.getFragment());
                mutate.uri(mutatedUri);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new ServerException(UserExceptionCode.SERVER_ERROR);
            }
        }
        ServerHttpRequest build = mutate.build();
        return chain.filter(exchange.mutate().request(build).build());
    }

    @Override
    public int getOrder() {
        return 10105;
    }

}
