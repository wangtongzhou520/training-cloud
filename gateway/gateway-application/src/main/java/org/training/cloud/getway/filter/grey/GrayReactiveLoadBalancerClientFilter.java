package org.training.cloud.getway.filter.grey;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.ReactiveLoadBalancerClientFilter;
import org.springframework.cloud.gateway.support.DelegatingServiceInstance;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

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

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        URI url = exchange.getAttribute(GATEWAY_REQUEST_URL_ATTR);
        String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
        if (url != null && ("grayLb".equals(url.getScheme()) || "grayLb".equals(schemePrefix))) {
            addOriginalRequestUrl(exchange, url);
            if (log.isTraceEnabled()) {
                log.trace(ReactiveLoadBalancerClientFilter.class.getSimpleName() + " url before: " + url);
            }
            return this.choose(exchange).doOnNext((response) -> {
                if (!response.hasServer()) {
                    throw NotFoundException.create(true, "Unable to find instance for " + url.getHost());
                } else {
                    URI uri = exchange.getRequest().getURI();
                    String overrideScheme = null;
                    if (schemePrefix != null) {
                        overrideScheme = url.getScheme();
                    }

                    DelegatingServiceInstance serviceInstance = new DelegatingServiceInstance((ServiceInstance) response.getServer(), overrideScheme);
                    URI requestUrl = this.reconstructURI(serviceInstance, uri);
                    if (log.isTraceEnabled()) {
                        log.trace("LoadBalancerClientFilter url chosen: " + requestUrl);
                    }

                    exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, requestUrl);
                }
            }).then(chain.filter(exchange));
        } else {
            return chain.filter(exchange);
        }
    }

    private Mono<Response<ServiceInstance>> choose(ServerWebExchange exchange) {
        URI uri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        NacosLoadBalancer loadBalancer =
                new NacosLoadBalancer(clientFactory.getLazyProvider(uri.getHost(), ServiceInstanceListSupplier.class), uri.getHost());
        return loadBalancer.choose(this.createRequest(exchange));
    }


    private Request createRequest(ServerWebExchange exchange) {
        HttpHeaders headers = exchange.getRequest().getHeaders();
        return new DefaultRequest<>(headers);
    }

    protected URI reconstructURI(ServiceInstance serviceInstance, URI original) {
        return LoadBalancerUriTools.reconstructURI(serviceInstance, original);
    }

    @Override
    public int getOrder() {
        return ReactiveLoadBalancerClientFilter.LOAD_BALANCER_CLIENT_FILTER_ORDER;
    }
}
