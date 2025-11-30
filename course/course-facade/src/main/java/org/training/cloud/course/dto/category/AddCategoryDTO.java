package org.training.cloud.course.dto.category;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@Schema(description = "课程分类新增")
public class AddCategoryDTO implements Serializable {

    @Schema(description = "分类名称")
    @NotBlank(message = "分类名称不能为空")
    private String name;

    @Schema(description = "父菜单ID（0表示顶级分类）")
    @NotNull(message = "父菜单ID不能为空")
    private Long parentId;

    @Schema(description = "分类层级（1-一级，2-二级，3-三级）")
    private Integer categoryLevel;

    @Schema(description = "排序序号")
    @NotNull(message = "排序序号不能为空")
    private Integer sort;

    @Schema(description = "分类图标URL")
    private String categoryIcon;

    @Schema(description = "分类描述")
    private String categoryDescription;

    @Schema(description = "是否显示（0-隐藏，1-显示）")
    private Boolean isShow;

}
