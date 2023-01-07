package com.springcloud.study.web.handler;

import com.springcloud.study.common.core.constant.ExceptionCode;
import com.springcloud.study.common.core.exception.BusinessException;
import com.springcloud.study.common.core.exception.ServerException;
import com.springcloud.study.common.core.vo.CommonResponse;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 公共的全局异常处理器
 *
 * @author wangtongzhou
 * @since 2020-08-11 17:35
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 参数异常校验
     *
     * @param ex 参数异常校验
     * @return 错误信息
     */
//    @ExceptionHandler({MethodArgumentNotValidException.class})
//    public CommonResponse methodArgumentTypeMismatchExceptionHandler(MethodArgumentNotValidException ex) {
//        FieldError fieldError = ex.getBindingResult().getFieldError();
//        assert fieldError != null;
//        return CommonResponse.error(ExceptionCode.P_PARAM.getCode(),
//                fieldError.getDefaultMessage(),
//                ExceptionUtils.getRootCauseMessage(ex));
//    }

    /**
     * 业务异常
     *
     * @param ex 业务异常
     * @return 错误信息
     */
    @ExceptionHandler({BusinessException.class})
    public CommonResponse businessExceptionExceptionHandler(BusinessException ex) {
        logger.debug("BusinessException", ex);
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

}
