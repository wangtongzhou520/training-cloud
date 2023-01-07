package com.springcloud.study.common.core.exception;

/**
 * 业务异常
 *
 * @author wangtongzhou
 * @since 2020-08-11 16:25
 */
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private String code;

    /**
     * 错误信息
     */
    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
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
