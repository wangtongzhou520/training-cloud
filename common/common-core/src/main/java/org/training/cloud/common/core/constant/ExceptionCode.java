package org.training.cloud.common.core.constant;

/**
 * 系统错误
 * P_XX 参数异常_错误信息
 * B_XX 业务异常_错误信息
 * S_XX 系统异常_错误信息
 *
 * @author wangtongzhou
 * @since 2020-05-31 21:15
 */
public class ExceptionCode {

    private String code;

    private String message;

    public ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public ExceptionCode setCode(String code) {
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
