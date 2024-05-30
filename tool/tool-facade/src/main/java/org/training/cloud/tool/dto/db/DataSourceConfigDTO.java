package org.training.cloud.tool.dto.db;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.training.cloud.common.core.vo.PageParam;

import javax.validation.constraints.NotNull;

/**
 * 数据源配置
 *
 * @author wangtongzhou
 * @since 2024-03-10 10:59
 */
@Data
public class DataSourceConfigDTO extends PageParam {

    @Schema(description = "数据源名称",  example = "test")
    private String name;
}
