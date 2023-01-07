package com.springcloud.study.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据库相关配置
 *
 * @author wangtongzhou
 * @since 2020-07-14 21:40
 */
@Configuration
@MapperScan("com.springcloud.study.system.dao")
public class DbConfig {
}
