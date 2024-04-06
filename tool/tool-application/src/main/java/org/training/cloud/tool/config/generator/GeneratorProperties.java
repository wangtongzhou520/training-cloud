package org.training.cloud.tool.config.generator;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;

/**
 * 员工考勤信息
 *
 * @author wangtongzhou 18635604249
 * @since 2024-04-06 16:36
 */
@ConfigurationProperties(prefix = "tool.codegen")
@Data
public class GeneratorProperties {


    @NotNull(message = "基础扫描包不能为空")
    private String basePackage;
}
