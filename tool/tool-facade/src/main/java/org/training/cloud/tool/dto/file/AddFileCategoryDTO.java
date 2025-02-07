package org.training.cloud.tool.dto.file;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@Schema(description = "文件分类新增")
public class AddFileCategoryDTO implements Serializable {

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
    private String remark;

}
