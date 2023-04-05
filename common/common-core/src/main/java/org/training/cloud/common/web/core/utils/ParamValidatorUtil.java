//package org.training.cloud.common.core.utils;
//
//import com.google.common.base.Preconditions;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Maps;
//import org.apache.commons.collections4.CollectionUtils;
//import org.apache.commons.collections4.MapUtils;
//
//import javax.validation.ConstraintViolation;
//import javax.validation.Validation;
//import javax.validation.Validator;
//import javax.validation.ValidatorFactory;
//import java.util.*;
//
///**
// * 参数校验
// *
// * @author wangtongzhou
// * @since 2020-06-18 16:03
// */
//public class ParamValidatorUtil {
//
//    private static ValidatorFactory validatorFactory =
//            Validation.buildDefaultValidatorFactory();
//
//
//    /**
//     * 单个类校验
//     *
//     * @param t      类
//     * @param groups 可变参数
//     * @param <T>    泛型
//     * @return 错误信息
//     */
//    private static <T> Map<String, String> validate(T t, Class... groups) {
//        Validator validator = validatorFactory.getValidator();
//        Set<ConstraintViolation<T>> validateResult = validator.validate(t, groups);
//        if (CollectionUtils.isEmpty(validateResult)) {
//            return Collections.emptyMap();
//        } else {
//            Map<String, String> errors = Maps.newHashMap();
//            for (ConstraintViolation<T> constraintViolation : validateResult) {
//                errors.put(constraintViolation.getPropertyPath().toString(),
//                        constraintViolation.getMessage());
//            }
//            return errors;
//        }
//    }
//
//    /**
//     * 校验集合
//     *
//     * @param collection 集合
//     * @param <T>        泛型
//     * @return 错误信息
//     */
//    private static <T> Map<String, String> validateList(Collection<?> collection) {
//        Preconditions.checkNotNull(collection);
//        Map<String, String> errors = Maps.newHashMap();
//        for (Object object : collection) {
//            Map<String, String> errorMap = validate(object, new Class[0]);
//            if (MapUtils.isNotEmpty(errorMap)) {
//                errors.putAll(errorMap);
//            }
//        }
//        return errors;
//    }
//
//    /**
//     * 参数校验
//     *
//     * @param first
//     * @param objects 可变参数
//     * @return
//     */
//    private static Map<String, String> validateObject(Object first,
//                                                      Object... objects) {
//        if (Objects.nonNull(objects) && objects.length > 0) {
//            return validateList(Lists.asList(first, objects));
//        } else {
//            return validate(first, new Class[0]);
//        }
//    }
//
//    /**
//     * 对外暴露参数校验接口
//     *
//     * @param params 参数
//     */
//    public static void check(Object params) {
//        Map<String, String> errors = validateObject(params);
//        if (MapUtils.isNotEmpty(errors)) {
//            //TODO 异常
//            //throw new ServerException(ExceptionCode.P_PARAM);
//        }
//    }
//}
