package com.springcloud.study.registry.erueka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * eureka服务端
 *
 * @author wangtongzhou
 * @since 2020-11-22 11:36
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudStudyEureka2 {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStudyEureka2.class, args);
    }
}
