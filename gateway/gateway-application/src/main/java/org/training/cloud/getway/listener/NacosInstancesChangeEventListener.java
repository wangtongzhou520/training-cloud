//package org.training.cloud.getway.listener;
//
//import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
//import com.alibaba.nacos.common.notify.Event;
//import com.alibaba.nacos.common.notify.listener.Subscriber;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.stereotype.Component;
//import org.training.cloud.common.core.utils.josn.JsonUtils;
//
//import javax.annotation.Resource;
//import static org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier.SERVICE_INSTANCE_CACHE_NAME;
//
//
///**
// * 动态感知服务上下线
// *
// * @author wangtongzhou
// * @since 2024-05-13 20:33
// */
//@Component
//@Slf4j
//public class NacosInstancesChangeEventListener extends Subscriber<InstancesChangeEvent> {
//
//    @Resource
//    private CacheManager defaultLoadBalancerCacheManager;
//
//    @Override
//    public void onEvent(InstancesChangeEvent event) {
//        log.info("Spring Gateway 接收实例刷新事件：{}, 开始刷新缓存", JsonUtils.toJsonString(event));
//        Cache cache = defaultLoadBalancerCacheManager.getCache(SERVICE_INSTANCE_CACHE_NAME);
//        if (cache != null) {
//            cache.evict(event.getServiceName());
//        }
//        log.info("Spring Gateway 实例刷新完成");
//    }
//
//    @Override
//    public Class<? extends Event> subscribeType() {
//        return InstancesChangeEvent.class;
//    }
//}
