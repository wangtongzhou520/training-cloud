package org.training.cloud.tool.dto.file;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 *
 * @author wangtongzhou
 * @since 2025-01-16 23:01
 */
@Data
@Accessors(chain = true)
@Schema(description = "文件管理修改")
public class ModifyFileDTO implements Serializable {
    @Schema(description = "文件ID")
    private Long id;

    @Schema(description = "分类ID")
    @NotNull(message = "分类ID不能为空")
    private Long categoryId;

    @Schema(description = "文件名称")
    @NotBlank(message = "文件名称不能为空")
    private String name;


}
