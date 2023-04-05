package org.training.cloud.common.web.core.constant;

/**
 * 用户看到的异常
 *
 * @author wangtongzhou 
 * @since 2023-03-30 07:09
 */
public interface UserExceptionCode {

    /**
     * 用户看到异常
     */
    ExceptionCode SUCCESS = new ExceptionCode(200, "成功");
    ExceptionCode BAD_REQUEST = new ExceptionCode(400, "请求参数不正确");
    ExceptionCode UNAUTHORIZED = new ExceptionCode(401, "账号未登录");
    ExceptionCode FORBIDDEN = new ExceptionCode(403, "没有该操作权限");
    ExceptionCode NOT_FOUND = new ExceptionCode(404, "请求未找到");
    ExceptionCode METHOD_NOT_ALLOWED = new ExceptionCode(405, "请求方法不正确");


    /**
     * 服务端异常
     */
    ExceptionCode SERVER_ERROR = new ExceptionCode(500, "系统异常");


    /**
     * 自定义异常
     */
    ExceptionCode UNKNOWN = new ExceptionCode(999, "未知错误");



}
