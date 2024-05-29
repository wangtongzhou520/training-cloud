package org.training.cloud.getway.filter.grey;

import com.alibaba.cloud.nacos.balancer.NacosBalancer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 灰度实现类
 *
 * @author wangtongzhou 18635604249
 * @since 2024-05-13 21:12
 */
@RequiredArgsConstructor
@Slf4j
public class NacosLoadBalancer implements ReactorServiceInstanceLoadBalancer {

    private static final String VERSION = "version";

    private static final String TAG = "service-tag";


    private final ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    private final String serviceId;


    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        //获取version
        HttpHeaders headers = ((RequestDataContext) request.getContext()).getClientRequest().getHeaders();
        //选择实例
        ServiceInstanceListSupplier supplier = serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        return supplier.get(request).next().map(list -> getInstanceResponse(list, headers));
    }


    private Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances, HttpHeaders headers) {
        //如果服务实例为空，则直接返回
        if (CollectionUtils.isEmpty(instances)) {
            log.warn("服务实例列表为空{}", serviceId);
            return new EmptyResponse();
        }

        //筛选满足version条件的实例列表
        String version = headers.getFirst(VERSION);
        List<ServiceInstance> chooseInstances;
        if (StringUtils.isBlank(version)) {
            chooseInstances = instances;
        } else {
            chooseInstances = instances.stream().filter(x -> StringUtils.equals(version, x.getMetadata().get(VERSION)))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(chooseInstances)) {
                log.warn("serviceId{}没有满足版本{}的服务实例列表", serviceId, version);
                chooseInstances = instances;
            }
        }

        //TODO 也可以基于TAG标签进行筛选

        return new DefaultResponse(NacosBalancer.getHostByRandomWeight3(chooseInstances));
    }

}
