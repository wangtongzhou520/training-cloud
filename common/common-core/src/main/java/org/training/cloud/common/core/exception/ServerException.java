package org.training.cloud.common.core.exception;

import org.training.cloud.common.core.constant.ExceptionCode;

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
    private Integer code;

    /**
     * 错误信息
     */
    private String message;

    public ServerException(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerException(ExceptionCode exceptionCode) {
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
