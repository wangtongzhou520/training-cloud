package org.training.cloud.common.security.core;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.training.cloud.common.security.core.annotations.NotAuthentication;

/**
 * 员工考勤信息
 *
 * @author wangtongzhou
 * @since 2023-04-07 22:26
 */
@Aspect
public class NotAuthenticationAop implements Ordered {

    @Around("@within(notAuthentication) || @annotation(notAuthentication)")
    public Object around(ProceedingJoinPoint point, NotAuthentication notAuthentication) throws Throwable {
        // 实际注入的inner实体由表达式后一个注解决定，即是方法上的@Inner注解实体，若方法上无@Inner注解，则获取类上的
        if (notAuthentication == null) {
            Class<?> clazz = point.getTarget().getClass();
            notAuthentication = AnnotationUtils.findAnnotation(clazz, NotAuthentication.class);
        }
//        String header = request.getHeader(SecurityConstants.FROM);
//        if (inner.value() && !StrUtil.equals(SecurityConstants.FROM_IN, header)) {
//            log.warn("访问接口 {} 没有权限", point.getSignature().getName());
//            throw new AccessDeniedException("Access is denied");
//        }
        return point.proceed();
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
