package org.training.cloud.system.constant;

import org.training.cloud.common.core.constant.ExceptionCode;

/**
 * 系统异常枚举
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
public interface SystemExceptionEnumConstants {

    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_FOUND = new ExceptionCode("OAUTH2_ACCESS_TOKEN_NOT_FOUND",
            "访问令牌不存在");
    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_EXPIRED = new ExceptionCode(
            "OAUTH2_ACCESS_TOKEN_NOT_EXPIRED", "访问令牌已过期");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_FOUND=new ExceptionCode(
            "OAUTH2_REFRESH_TOKEN_NOT_FOUND","刷新令牌不存在");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_EXPIRED=new ExceptionCode(
            "OAUTH2_REFRESH_TOKEN_NOT_EXPIRED","刷新令牌已过期");
}
