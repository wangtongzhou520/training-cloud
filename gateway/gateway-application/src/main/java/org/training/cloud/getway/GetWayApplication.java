package org.training.cloud.getway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关
 *
 * @author wangtongzhou
 * @since 2023-04-24 20:56
 */
@SpringBootApplication
public class GetWayApplication {


    public static void main(String[] args) {
        SpringApplication.run(GetWayApplication.class, args);
    }
}
