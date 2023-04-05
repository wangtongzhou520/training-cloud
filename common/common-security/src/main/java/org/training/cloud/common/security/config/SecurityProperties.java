package org.training.cloud.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 员工考勤信息
 *
 * @author wangtongzhou 
 * @since 2023-04-05 09:47
 */
@ConfigurationProperties(prefix = "auth.security")
@Data
public class SecurityProperties {


    private List<String> notAuthUrls;
}
