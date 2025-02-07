package org.training.cloud.course.dto.category;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(description = "课程分类修改")
public class ModifyCategoryDTO implements Serializable {

    @Schema(description = "分类ID")
    private Long id;

    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String categoryName;

    @Schema(description = "父菜单ID")
    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    @Schema(description = "排序序号")
    @NotNull(message = "排序序号不能为空")
    private Integer sort;


    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态（0：未删除，1：已删除）不能为空")
    private Boolean deleteState;

}
