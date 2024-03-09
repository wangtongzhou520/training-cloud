package org.training.cloud.tool.vo.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 表
 *
 * @author wangtongzhou 18635604249
 * @since 2024-03-09 16:50
 */
@Data
public class DatabaseTableVO implements Serializable {

    @Schema(description = "表名称",  example = "sys_user")
    private String name;

    @Schema(description = "表描述",  example = "用户表")
    private String comment;
}
