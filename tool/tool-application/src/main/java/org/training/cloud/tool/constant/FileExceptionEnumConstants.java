package org.training.cloud.tool.constant;



import org.training.cloud.common.core.constant.ExceptionCode;


/**
 * 文件异常常量
 *
 * @author wangtongzhou
 * @since 2024-04-21 21:35
 */
public interface FileExceptionEnumConstants {


    // ========== 文件 ==========

    ExceptionCode FILE_NOT_EXISTS = new ExceptionCode(102001001,"文件不存在");


    ExceptionCode FILE_CATEGORY_NOT_EXISTS = new ExceptionCode(102001002,
            "文件分类不存在");


    ExceptionCode FILE_CATEGORY_EXISTS = new ExceptionCode(102001002,
            "文件分类已存在");


}
