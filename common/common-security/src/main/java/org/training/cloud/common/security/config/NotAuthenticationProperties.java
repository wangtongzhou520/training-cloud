package org.training.cloud.common.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 配置文件
 *
 * @author wangtongzhou
 * @since 2023-04-05 09:47
 */
@ConfigurationProperties(prefix = "auth.security")
@Data
public class NotAuthenticationProperties {

    /**
     * Token Header
     */

    private String tokenHeader = "Authorization";


    /**
     * 不需要认证的URL
     */

    private List<String> notAuthUrls;

}
