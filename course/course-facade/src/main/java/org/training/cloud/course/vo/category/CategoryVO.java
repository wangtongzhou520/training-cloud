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

    @Schema(description = "排序序号")
    private Integer sort;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;



}
