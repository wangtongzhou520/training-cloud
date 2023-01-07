package com.springcloud.study.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统相关应用
 *
 * @since 2020-05-24 10:11
 * @author wangtongzhou
 */
@SpringBootApplication(scanBasePackages = {"com.springcloud.study"})
@EnableDiscoveryClient
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SystemApplication.class, args);
    }
}
