package org.training.cloud.getway.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.training.cloud.common.core.constant.UserExceptionCode;
import org.training.cloud.common.core.utils.josn.JsonUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import reactor.core.publisher.Mono;

/**
 * 全局异常
 *
 * @author wangtongzhou
 * @since 2024-05-30 21:11
 */
@Component
@Order(-1)
@Slf4j
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        ServerHttpRequest request = exchange.getRequest();
        log.error("请求地址{}参数{}发生异常", request.getURI(), request.getMethod(), ex);
        CommonResponse<?> result;
        if (ex instanceof ResponseStatusException) {
            result = CommonResponse.error(((ResponseStatusException) ex).getRawStatusCode(),
                    ((ResponseStatusException) ex).getReason());
        } else {
            result = CommonResponse.error(UserExceptionCode.SERVER_ERROR);
        }

        // 设置 header
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        // 设置 body
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory bufferFactory = response.bufferFactory();
            try {
                return bufferFactory.wrap(JsonUtils.toJsonByte(result));
            } catch (Exception exception) {
                log.error("JSON写入异常请求{}参数{}", request.getURI(), request.getMethod(), ex);
                return bufferFactory.wrap(new byte[0]);
            }
        }));
    }
}
