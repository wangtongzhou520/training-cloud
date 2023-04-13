package org.training.cloud.common.web.core.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.training.cloud.common.web.core.constant.ExceptionCode;
import org.training.cloud.common.web.core.constant.UserExceptionCode;
import org.training.cloud.common.web.core.exception.BusinessException;

import java.io.Serializable;
import java.util.Objects;

/**
 * 公共返回
 *
 * @author wangtongzhou
 * @since 2020-05-23 13:44
 */
public class CommonResponse<T> implements Serializable {


    /**
     * 提示信息
     */
    private String message;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态
     */
    private Boolean state;

    /**
     * 错误明细
     */
    private String detailMessage;


    /**
     * 成功
     *
     * @param <T> 泛型
     * @return 返回结果
     */
    public static <T> CommonResponse<T> ok() {
        return ok(null);
    }

    /**
     * 成功
     *
     * @param data 传入的对象
     * @param <T>  泛型
     * @return 返回结果
     */
    public static <T> CommonResponse<T> ok(T data) {
        CommonResponse<T> response = new CommonResponse<T>();
        response.code = UserExceptionCode.SUCCESS.getCode();
        response.data = data;
        response.message = "返回成功";
        response.state = true;
        return response;
    }


    public static <T> CommonResponse<T> error(ExceptionCode errorCode) {
        return error(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 错误
     *
     * @param code    自定义code
     * @param message 自定义返回信息
     * @param <T>     泛型
     * @return 返回信息
     */
    public static <T> CommonResponse<T> error(Integer code, String message) {
        return error(code, message, null);
    }

    /**
     * 错误
     *
     * @param code          自定义code
     * @param message       自定义返回信息
     * @param detailMessage 错误详情信息
     * @param <T>           泛型
     * @return 返回错误信息
     */
    public static <T> CommonResponse<T> error(Integer code, String message,
                                              String detailMessage) {
        CommonResponse<T> response = new CommonResponse<T>();
        response.code = code;
        response.data = null;
        response.message = message;
        response.state = false;
        response.detailMessage = detailMessage;
        return response;
    }

    public Boolean getState() {
        return state;
    }

    public CommonResponse<T> setState(Boolean state) {
        this.state = state;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResponse<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public CommonResponse<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public CommonResponse<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public CommonResponse<T> setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
        return this;
    }


    public static boolean isSuccess(Integer code) {
        return Objects.equals(code, UserExceptionCode.SUCCESS.getCode());
    }

    @JsonIgnore
    public boolean isSuccess() {
        return isSuccess(code);
    }

    public void checkError() throws BusinessException {
        if (isSuccess()) {
            return;
        }
        throw new BusinessException(code, message);
    }

    @JsonIgnore
    public T getApiData() {
        checkError();
        return data;
    }

}

