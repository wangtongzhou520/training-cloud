package org.training.cloud.tool.vo.file;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

import static org.training.cloud.common.core.utils.date.DateUtils.DATE_TIME_PATTERN;

/**
 * 文件返回信息
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:18
 */


@Data
@Accessors(chain = true)
@Schema(description = "文件返回信息")
public class FileVO implements Serializable {
    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "文件名")
    private String name;

    @Schema(description = "文件路径")
    private String path;

    @Schema(description = "url", example = "https://baidu.com")
    private String url;

    @Schema(description = "文件后缀")
    private String suffix;

    @Schema(description = "文件类型,1文件0图片2视频", example = "2")
    private Integer type;

    @Schema(description = "文件大小")
    private Integer size;
}
