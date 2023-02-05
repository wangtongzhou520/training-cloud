package org.training.cloud.order.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.google.common.collect.Lists;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 优选选择相同集群
 *
 * @author wangtongzhou
 * @since 2020-12-13 14:56
 */
public class SelectSameClusterRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        //获取当前的集群名称
        String currentClusterName = discoveryProperties.getClusterName();
        NamingService namingService = nacosServiceManager
                .getNamingService(discoveryProperties.getNacosProperties());
        //负载均衡对象
        BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
        //获取当前调用的服务名称
        String invokedServiceName = baseLoadBalancer.getName();

        List<Instance> sameClusterInstances = Lists.newArrayList();
        List<Instance> allInstances;
        try {
            allInstances = namingService.getAllInstances(invokedServiceName);
            for (Instance instance : allInstances) {
                if (StringUtils.endsWithIgnoreCase(instance.getClusterName(),
                        currentClusterName)) {
                    sameClusterInstances.add(instance);
                }
            }
            Instance chooseInstance;
            if (CollectionUtils.isEmpty(sameClusterInstances)) {
                //跨集群调用
                chooseInstance = WeightBalancer.chooseInstanceByRandomWeight(allInstances);
            } else {
                //相同集群调用
                chooseInstance = WeightBalancer.chooseInstanceByRandomWeight(sameClusterInstances);
            }

            return new NacosServer(chooseInstance);
        } catch (NacosException e) {
            //记录日志
        }

        return null;
    }
}
