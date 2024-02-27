package org.training.cloud.common.core.constant;

/**
 * 错误码定义
 *
 *
 *
 * @author wangtongzhou
 * @since 2020-05-31 21:15
 */
public class ExceptionCode {

    private Integer code;

    private String message;

    public ExceptionCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public ExceptionCode setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ExceptionCode setMessage(String message) {
        this.message = message;
        return this;
    }
}
