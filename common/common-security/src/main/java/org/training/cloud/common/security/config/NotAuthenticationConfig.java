package org.training.cloud.common.security.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.training.cloud.common.security.core.annotations.NotAuthentication;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 资源服务器对外直接暴露URL
 *
 * @author wangtongzhou
 * @since 2023-03-19 15:47
 */
@AutoConfiguration
@AllArgsConstructor
@EnableConfigurationProperties(NotAuthenticationProperties.class)
public class NotAuthenticationConfig implements InitializingBean, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Autowired
    private NotAuthenticationProperties notAuthenticationProperties;


    @Getter
    @Setter
    private List<String> permitAllUrls;


    @Override
    public void afterPropertiesSet() {
        permitAllUrls.addAll(notAuthenticationProperties.getNotAuthUrls());
        RequestMappingHandlerMapping mapping = (RequestMappingHandlerMapping) applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : map.entrySet()) {
            HandlerMethod handlerMethod = entry.getValue();
            if (!handlerMethod.hasMethodAnnotation(NotAuthentication.class)) {
                continue;
            }
            if (entry.getKey().getPatternsCondition() == null) {
                continue;
            }
            Set<String> urls = entry.getKey().getPatternsCondition().getPatterns();
            permitAllUrls.addAll(urls);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
