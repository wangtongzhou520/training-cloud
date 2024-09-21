package org.training.cloud.tool.vo.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据源列表
 *
 * @author wangtongzhou
 * @since 2024-03-10 11:00
 */
@Data
public class DataSourceConfigVO implements Serializable {

    private Long id;


    @Schema(description = "数据源名称",  example = "system")
    private String name;

    /**
     * 数据源连接
     */
    @Schema(description = "数据源连接",  example = "url")
    private String url;
    /**
     * 用户名
     */
    @Schema(description = "用户名",  example = "root")
    private String username;

}
