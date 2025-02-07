package org.training.cloud.tool.dto.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
/**
 * 新增文件
 *
 * @author wangtongzhou
 * @since 2024-04-21 17:09
 */
@Data
@Accessors(chain = true)
@Schema(description = "文件新增")
public class AddFileDTO implements Serializable {
    @Schema(description = "分类ID")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @Schema(description = "文件名")
    @NotBlank(message = "文件名不能为空")
    private String name;

    @Schema(description = "文件路径")
    @NotBlank(message = "文件路径不能为空")
    private String path;

    @Schema(description = "url", example = "https://baidu.com")
    @NotBlank(message = "url不能为空")
    private String url;

    @Schema(description = "文件类型")
    private String type;

    @Schema(description = "文件大小")
    private Long size;

}
