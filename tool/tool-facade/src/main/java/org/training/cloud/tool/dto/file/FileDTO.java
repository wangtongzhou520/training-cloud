package org.training.cloud.tool.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


import static org.training.cloud.common.core.utils.date.DateUtils.DATE_TIME_PATTERN;

/**
 * 文件分页查询
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:05
 */
@Data
@Accessors(chain = true)
@Schema(description = "文件分页查询")
public class FileDTO extends PageParam {

    @Schema(description = "文件名称")
    private Long categoryId;

    @Schema(description = "文件名称")
    private String name;

    @Schema(description = "文件类型" )
    private String type;


}
