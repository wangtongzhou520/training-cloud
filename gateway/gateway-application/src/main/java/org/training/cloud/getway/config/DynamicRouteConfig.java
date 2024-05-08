package org.training.cloud.getway.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.training.cloud.getway.route.NacosRouteDefinitionRepository;

import javax.annotation.Resource;

/**
 * 动态路由配置
 *
 * @author wangtongzhou
 * @since 2024-05-08 20:21
 */
@Configuration
@ConditionalOnProperty(prefix = "gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {

    @Resource
    private ApplicationEventPublisher publisher;

    @Configuration
    @ConditionalOnProperty(prefix = "gateway.dynamicRoute", name = "dataType", havingValue = "nacos", matchIfMissing = true)
    public class NacosDynRoute {
        @Resource
        private NacosConfigProperties nacosConfigProperties;

        @Bean
        public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
            return new NacosRouteDefinitionRepository(publisher, nacosConfigProperties);
        }
    }

}
