package com.springcloud.study.order.config;

import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;

import java.util.List;

/**
 * 权重算法
 *
 * @author wangtongzhou
 * @since 2020-12-13 16:48
 */
public class WeightBalancer extends Balancer {

    public static Instance chooseInstanceByRandomWeight(List<Instance> instances) {
        return getHostByRandomWeight(instances);
    }
}
