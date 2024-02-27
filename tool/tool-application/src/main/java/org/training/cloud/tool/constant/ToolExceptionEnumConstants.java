package org.training.cloud.tool.constant;

import org.training.cloud.common.core.constant.ExceptionCode;

/**
 * 工具类异常配置
 *
 * @author wangtongzhou 18635604249
 * @since 2024-02-26 20:51
 */
public interface ToolExceptionEnumConstants {

    // ========== 数据源配置 ==========
    ExceptionCode DATA_SOURCE_CONFIG_NOT_EXISTS = new ExceptionCode(101001001, "数据源配置不存在");
    ExceptionCode DATA_SOURCE_CONFIG_ERROR = new ExceptionCode(101001002, "数据源配置不正确，无法进行连接");

    // ========== 代码生成器 101002000 ==========
    ExceptionCode GENERATOR_TABLE_EXISTS = new ExceptionCode(101002001, "表定义已经存在");

    ExceptionCode GENERATOR_TABLE_NULL = new ExceptionCode(101002002, "表不存在");

    ExceptionCode GENERATOR_TABLE_COLUMN_NULL = new ExceptionCode(101002003, "表字段不存在");

    ExceptionCode GENERATOR_TABLE_COMMENT_NULL = new ExceptionCode(101002004,
            "表注释不存在");





}
