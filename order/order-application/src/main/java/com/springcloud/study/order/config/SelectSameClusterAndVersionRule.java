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
 * 优先选择相同的集群和版本 金丝雀发布
 *
 * @author wangtongzhou
 * @since 2020-12-13 17：01
 */
public class SelectSameClusterAndVersionRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object key) {
        NamingService namingService = nacosServiceManager
                .getNamingService(discoveryProperties.getNacosProperties());
        try {
            //获取同集群同版本实例
            List<Instance> querySameClusterAndVersionInstances =
                    querySameClusterAndVersionInstances(discoveryProperties,
                            namingService);
            Instance chooseInstance;
            if (CollectionUtils.isEmpty(querySameClusterAndVersionInstances)) {
                //跨集群调用相同的实例信息
                chooseInstance =
                        queryCrossClusterAndSameVersion(discoveryProperties,
                                namingService);
            } else {
                chooseInstance =
                        WeightBalancer.chooseInstanceByRandomWeight(querySameClusterAndVersionInstances);
            }
            return new NacosServer(chooseInstance);
        } catch (NacosException ex) {

        }
        return null;
    }

    /**
     * 跨集群获取相同版本的服务实例
     *
     * @param discoveryProperties 基础配置信息
     * @param namingService       服务实例
     * @return 跨集群实例信息
     */
    private Instance queryCrossClusterAndSameVersion(NacosDiscoveryProperties discoveryProperties, NamingService namingService) throws NacosException {
        //获取当前的版本号
        String currentVersion = discoveryProperties.getMetadata().get(
                "current‐version");
        //获取到所有的实例信息
        List<Instance> allInstances = getAllInstances(namingService);
        List<Instance> sameVersionInstances = Lists.newArrayList();
        for (Instance instance : allInstances) {
            if (StringUtils.endsWithIgnoreCase(currentVersion,
                    instance.getMetadata().get("current‐version"))) {
                sameVersionInstances.add(instance);
            }
        }
        Instance chooseInstance;
        if (CollectionUtils.isEmpty(sameVersionInstances)) {
            throw new RuntimeException("找不到相同版本的微服务实例");
        } else {
            chooseInstance=WeightBalancer.chooseInstanceByRandomWeight(sameVersionInstances);
        }
        return chooseInstance;
    }

    /**
     * 获取同集群同版本的实例
     *
     * @param discoveryProperties 基础配置信息
     * @param namingService       服务信息
     * @return 同集群同版本的实例
     */
    private List<Instance> querySameClusterAndVersionInstances(NacosDiscoveryProperties discoveryProperties, NamingService namingService) throws NacosException {
        //获取当前的集群名称
        String currentClusterName = discoveryProperties.getClusterName();
        //获取当前的版本号
        String currentVersion = discoveryProperties.getMetadata().get(
                "current‐version");
        //获取到所有的实例信息
        List<Instance> allInstances = getAllInstances(namingService);
        List<Instance> selectSameClusterAndVersionInstances =
                Lists.newArrayList();
        //过滤相同
        for (Instance instance : allInstances) {
            if (StringUtils.endsWithIgnoreCase(currentClusterName,
                    instance.getClusterName()) && StringUtils.endsWithIgnoreCase(currentVersion,
                    instance.getMetadata().get("current‐version"))) {
                selectSameClusterAndVersionInstances.add(instance);
            }
        }
        return selectSameClusterAndVersionInstances;
    }

    /**
     * 获取所有的服务实例
     *
     * @param namingService 服务信息
     * @return 所有的服务实例
     */
    private List<Instance> getAllInstances(NamingService namingService) throws NacosException {
        //获取负载均衡器对象
        BaseLoadBalancer baseLoadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
        //获取服务的名称
        String invokedServiceName = baseLoadBalancer.getName();
        //获取所有的服务实例
        List<Instance> instances =
                namingService.getAllInstances(invokedServiceName);
        return instances;
    }
}
