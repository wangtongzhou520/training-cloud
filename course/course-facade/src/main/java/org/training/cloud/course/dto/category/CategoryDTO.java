package org.training.cloud.course.dto.category;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;


@Data
@Accessors(chain = true)
@Schema(description = "课程分类分页查询")
public class CategoryDTO extends PageParam {

    @Schema(description = "分类ID")
    private Long id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "排序序号")
    private Integer sort;


}


