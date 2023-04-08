package org.training.cloud.system.constant;

import org.training.cloud.common.web.core.constant.ExceptionCode;

/**
 * 系统异常枚举
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
public interface SystemExceptionEnumConstants {



    /**
     * Oauth2
     */
    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_FOUND = new ExceptionCode(100001001, "访问令牌不存在");
    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_EXPIRED = new ExceptionCode(100001002, "访问令牌已过期");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_FOUND=new ExceptionCode(100001003,"刷新令牌不存在");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_EXPIRED= new ExceptionCode(100001004,"刷新令牌已过期");
    ExceptionCode OAUTH2_CLIENT_EXIST= new ExceptionCode(100001005, "授权客户端编号已存在");
    ExceptionCode OAUTH2_CLIENT_NOT_EXIST= new ExceptionCode(100001006, "授权客户端不存在");
    ExceptionCode OAUTH2_CLIENT_DISABLE= new ExceptionCode(100001007,
            "授权客户端被禁用");





    /**
     * auth
     */
    ExceptionCode AUTH_NOT_FOUND = new ExceptionCode(100002001,
            "登录失败，用户名或者密码不存在");
    ExceptionCode AUTH_ACCOUNT_FAIL = new ExceptionCode(100002002,
            "登录失败，用户名或者密码不正确");
    ExceptionCode AUTH_ACCOUNT_DISABLE = new ExceptionCode(100002003,
            "登录失败，用户名不可用");

}
