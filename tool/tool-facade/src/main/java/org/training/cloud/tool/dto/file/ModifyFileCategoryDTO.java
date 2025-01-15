package org.training.cloud.tool.dto.file;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Schema(description = "文件分类修改")
public class ModifyFileCategoryDTO implements Serializable {

    @Schema(description = "文件分类ID")
    @NotNull(message = "文件分类ID不能为空")
    private Long id;

    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @Schema(description = "父分类ID")
    @NotNull(message = "父分类ID不能为空")
    private Long parentId;

    @Schema(description = "排序序号")
    @NotNull(message = "排序序号不能为空")
    private Integer seq;

    @Schema(description = "备注")
    @NotBlank(message = "备注不能为空")
    private String remark;


}
