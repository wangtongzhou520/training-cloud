package com.springcloud.study.common.core.exception;

import com.springcloud.study.common.core.constant.ExceptionCode;

/**
 * 服务端异常
 *
 * @author wangtongzhou
 * @since 2020-05-31 21:02
 */
public class ServerException extends RuntimeException {

    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public ServerException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
