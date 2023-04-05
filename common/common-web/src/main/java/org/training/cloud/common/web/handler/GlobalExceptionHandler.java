package org.training.cloud.common.web.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.training.cloud.common.web.core.exception.BusinessException;
import org.training.cloud.common.web.core.exception.ServerException;
import org.training.cloud.common.web.core.vo.CommonResponse;
import org.training.cloud.common.web.core.constant.UserExceptionCode;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.nio.file.AccessDeniedException;

/**
 * 公共的全局异常处理器
 *
 * @author wangtongzhou
 * @since 2020-08-11 17:35
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 请求参数缺失
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MissingServletRequestParameterException.class})
    public CommonResponse<?> missingServletRequestParameterExceptionHandler(MissingServletRequestParameterException ex) {
        return CommonResponse.error(UserExceptionCode.BAD_REQUEST.getCode(), String.format("请求参数缺失:%s", ex.getParameterName()));
    }


    /**
     * 请求参数类型错误
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResponse<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        return CommonResponse.error(UserExceptionCode.BAD_REQUEST.getCode(), String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    /**
     * 参数校验不正确
     *
     * @param ex
     * @return
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public CommonResponse<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null;
        return CommonResponse.error(UserExceptionCode.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 参数绑定不正确
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public CommonResponse<?> bindExceptionHandler(BindException ex) {
//        log.warn("[handleBindException]", ex);
        FieldError fieldError = ex.getFieldError();
        assert fieldError != null;
        return CommonResponse.error(UserExceptionCode.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }


    /**
     * 参数校验不通过
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResponse<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
//        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return CommonResponse.error(UserExceptionCode.BAD_REQUEST.getCode(), String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    /**
     * 请求方法不正确
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponse<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
//        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return CommonResponse.error(UserExceptionCode.METHOD_NOT_ALLOWED.getCode(), String.format("请求方法不正确:%s", ex.getMessage()));
    }


    /**
     * 请求地址不存在
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public CommonResponse<?> noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
//        log.warn("[noHandlerFoundExceptionHandler]", ex);
        return CommonResponse.error(UserExceptionCode.NOT_FOUND.getCode(), String.format("请求地址不存在:%s", ex.getRequestURL()));
    }


    /**
     * 权限不够
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResponse<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
//        log.warn("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})]", WebFrameworkUtils.getLoginUserId(req),
//                req.getRequestURL(), ex);
        return CommonResponse.error(UserExceptionCode.FORBIDDEN);
    }


    /**
     * 业务异常
     *
     * @param ex 业务异常
     * @return 错误信息
     */
    @ExceptionHandler({BusinessException.class})
    public CommonResponse businessExceptionExceptionHandler(BusinessException ex) {
//        logger.debug("BusinessException", ex);
        return CommonResponse.error(ex.getCode(), ex.getMessage());
    }

    /**
     * 服务异常
     *
     * @param ex 服务异常
     * @return 错误信息
     */
    @ExceptionHandler({ServerException.class})
    public CommonResponse serverExceptionExceptionHandler(ServerException ex) {
        logger.debug("ServerException", ex);
        return CommonResponse.error(ex.getCode(), ex.getMessage());
    }


    /**
     * 处理系统异常
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<?> serviceExceptionHandler(HttpServletRequest req, Throwable ex) {
        return CommonResponse.error(UserExceptionCode.SERVER_ERROR.getCode(),
                UserExceptionCode.SERVER_ERROR.getMessage());
    }


    /**
     * filter异常处理
     *
     * @param request
     * @param ex
     * @return
     */
    public CommonResponse<?> allExceptionHandler(HttpServletRequest request, Throwable ex) {
        if (ex instanceof MissingServletRequestParameterException) {
            return missingServletRequestParameterExceptionHandler((MissingServletRequestParameterException) ex);
        }
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return methodArgumentTypeMismatchExceptionHandler((MethodArgumentTypeMismatchException) ex);
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return methodArgumentNotValidExceptionHandler((MethodArgumentNotValidException) ex);
        }
        if (ex instanceof BindException) {
            return bindExceptionHandler((BindException) ex);
        }
        if (ex instanceof ConstraintViolationException) {
            return constraintViolationExceptionHandler((ConstraintViolationException) ex);
        }
        if (ex instanceof NoHandlerFoundException) {
            return noHandlerFoundExceptionHandler((NoHandlerFoundException) ex);
        }
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            return httpRequestMethodNotSupportedExceptionHandler((HttpRequestMethodNotSupportedException) ex);
        }
        if (ex instanceof BusinessException) {
            return businessExceptionExceptionHandler((BusinessException) ex);
        }
        if (ex instanceof AccessDeniedException) {
            return accessDeniedExceptionHandler(request, (AccessDeniedException) ex);
        }
        return serviceExceptionHandler(request, ex);
    }

}
