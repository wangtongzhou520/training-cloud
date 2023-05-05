package org.training.cloud.common.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.training.cloud.common.redis.core.RedisUtils;

/**
 * 员工考勤信息
 *
 * @author wangtongzhou 
 * @since 2023-04-21 07:14
 */
@AutoConfiguration
public class RedisAutoConfig {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Bean
    public RedisUtils redisUtils() {
        return new  RedisUtils(redisTemplate);
    }

}
