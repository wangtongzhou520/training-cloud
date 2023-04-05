package org.training.cloud.common.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.training.cloud.common.security.core.annotations.NotAuthentication;

import java.util.*;

/**
 * 资源服务器对外直接暴露URL
 *
 * @author wangtongzhou
 * @since 2023-03-19 15:47
 */
@AutoConfiguration
@EnableConfigurationProperties(SecurityProperties.class)
public class NotAuthenticationConfig implements InitializingBean, ApplicationContextAware {

    private static final String PATTERN = "\\{(.*?)}";

    public static final String ASTERISK = "*";


    private ApplicationContext applicationContext;

    @Autowired
    private SecurityProperties securityProperties;

    @Getter
    @Setter
    private List<String> permitAllUrls = new ArrayList<>();

    @Override
    public void afterPropertiesSet() throws Exception {
        permitAllUrls.addAll(securityProperties.getNotAuthUrls());
        RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping)
                applicationContext.getBean("requestMappingHandlerMapping");
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping.getHandlerMethods();
        map.keySet().forEach(x -> {
            HandlerMethod handlerMethod = map.get(x);

            // 获取方法上边的注解 替代path variable 为 *
            NotAuthentication method = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), NotAuthentication.class);
            Optional.ofNullable(method).ifPresent(inner -> Objects.requireNonNull(x.getPathPatternsCondition())
                    .getPatternValues().forEach(url -> permitAllUrls.add(url.replaceAll(PATTERN, ASTERISK))));

            // 获取类上边的注解, 替代path variable 为 *
            NotAuthentication controller = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), NotAuthentication.class);
            Optional.ofNullable(controller).ifPresent(inner -> Objects.requireNonNull(x.getPathPatternsCondition())
                    .getPatternValues().forEach(url -> permitAllUrls.add(url.replaceAll(PATTERN, ASTERISK))));
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
