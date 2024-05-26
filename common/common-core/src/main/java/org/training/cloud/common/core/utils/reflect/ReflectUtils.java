package org.training.cloud.common.core.utils.reflect;

import java.lang.reflect.Field;

/**
 * 反射
 *
 * @author wangtongzhou
 * @since 2024-05-15 22:40
 */
public class ReflectUtils {

    public static Object getFieldValue(Object target, String fieldName) {
        // 获取目标对象的Class
        Class<?> clazz = target.getClass();
        // 获取指定字段
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
        // 如果字段是私有的，需要取消访问限制
        if (field.isAccessible() == false) {
            field.setAccessible(true);
        }
        // 获取字段值
        try {
            return field.get(target);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
