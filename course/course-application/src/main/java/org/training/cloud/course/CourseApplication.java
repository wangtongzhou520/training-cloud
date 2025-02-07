package org.training.cloud.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 课程中心
 *
 * @author wangtongzhou
 * @since 2025-01-19 22:00
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(CourseApplication.class, args);
    }
}
