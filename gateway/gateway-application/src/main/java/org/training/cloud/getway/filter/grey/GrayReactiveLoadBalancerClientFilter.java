package org.training.cloud.getway.filter.grey;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Set;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;

/**
 * 灰度功能
 *
 * @author wangtongzhou
 * @since 2024-05-27 21:59
 */
@Component
@Slf4j
@AllArgsConstructor
public class GrayReactiveLoadBalancerClientFilter implements GlobalFilter, Ordered {


    private final LoadBalancerClientFactory clientFactory;
    private LoadBalancerProperties properties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
        if (url != null && ("grayLb".equals(url.getScheme()) || "grayLb".equals(schemePrefix))) {
            addOriginalRequestUrl(exchange, url);
            if (log.isTraceEnabled()) {
                log.trace(ReactiveLoadBalancerClientFilter.class.getSimpleName() + " url before: " + url);
            }
            URI requestUri = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
            String serviceId = requestUri.getHost();
            Set<LoadBalancerLifecycle> supportedLifecycleProcessors = LoadBalancerLifecycleValidator
                    .getSupportedLifecycleProcessors(clientFactory.getInstances(serviceId, LoadBalancerLifecycle.class),
                            RequestDataContext.class, ResponseData.class, ServiceInstance.class);
            DefaultRequest<RequestDataContext> lbRequest = new DefaultRequest<>(
                    new RequestDataContext(new RequestData(exchange.getRequest()), getHint(serviceId)));
        }else {

        }

        return null;
    }

    @Override
    public int getOrder() {
        return ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }
}
