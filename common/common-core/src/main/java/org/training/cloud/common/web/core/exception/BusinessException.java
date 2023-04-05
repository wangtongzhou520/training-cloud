package org.training.cloud.common.web.core.exception;

import org.training.cloud.common.web.core.constant.ExceptionCode;

/**
 * 业务异常
 * 系统-模块-错误码 9位数
 * 系统
 * 100  用户系统
 * <p>
 * 模块
 * 001  认证模块
 * 002  用户
 * <p>
 * 错误码
 * 001  具体错误
 *
 * @author wangtongzhou
 * @since 2020-08-11 16:25
 */
public class BusinessException extends RuntimeException {
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public BusinessException(ExceptionCode exceptionCode) {
        this.code = exceptionCode.getCode();
        this.message = exceptionCode.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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
