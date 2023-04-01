package org.training.cloud.system.constant;

import org.training.cloud.common.core.constant.ExceptionCode;

/**
 * 系统异常枚举
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
public interface SystemExceptionEnumConstants {


    /**
     * auth
     */
    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_FOUND = new ExceptionCode(100001001, "访问令牌不存在");
    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_EXPIRED = new ExceptionCode(100001002, "访问令牌已过期");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_FOUND=new ExceptionCode(100001003,"刷新令牌不存在");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_EXPIRED= new ExceptionCode(100001004,"刷新令牌已过期");
}
