package org.training.cloud.course.vo.category;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@Schema(description = "课程分类返回信息")
public class CategoryVO implements Serializable {

    @Schema(description = "分类ID")
    private Long id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "分类层级（1-一级，2-二级，3-三级）")
    private Integer categoryLevel;

    @Schema(description = "排序序号")
    private Integer sort;

    @Schema(description = "分类图标URL")
    private String categoryIcon;

    @Schema(description = "分类描述")
    private String categoryDescription;

    @Schema(description = "是否显示（0-隐藏，1-显示）")
    private Boolean isShow;

    @Schema(description = "课程数量")
    private Integer courseCount;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;

}
