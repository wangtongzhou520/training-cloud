package org.training.cloud.test1;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * feign client
 *
 * @author wangtongzhou
 * @since 2020-12-18 13:28
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OpenFeignTest {

    public static void main(String[] args) {
        SpringApplication.run(OpenFeignTest.class);
    }
}
