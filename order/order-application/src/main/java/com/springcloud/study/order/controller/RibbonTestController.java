package com.springcloud.study.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * ribbon 测试
 *
 * @author wangtongzhou
 * @since
 */
@RestController
public class RibbonTestController {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-system-service}")
    private String serverURL;

    @GetMapping("/test")
    public String test() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(serverURL + "/sys/depts",
                String.class);
        return responseEntity.getBody();
    }

}
