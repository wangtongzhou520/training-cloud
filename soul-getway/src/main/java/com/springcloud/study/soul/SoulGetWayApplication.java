package org.training.cloud.soul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动
 *
 * @author wangtongzhou
 * @since 2020-12-24 20:22
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SoulGetWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SoulGetWayApplication.class, args);
    }
}
