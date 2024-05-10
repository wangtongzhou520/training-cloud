package org.training.cloud.getway.filter.log;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 访问日志
 *
 * @author wangtongzhou
 * @since 2024-05-10 21:03
 */
@Component
public class AccessLogFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //TODO 待设计
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
