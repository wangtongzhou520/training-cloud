package org.training.cloud.course.dto.course;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(description = "课程修改")
public class ModifyCourseDTO implements Serializable {

    @Schema(description = "课程ID")
    private Long id;

    @Schema(description = "课程名称")
    @NotBlank(message = "课程名称不能为空")
    private String courseName;

    @Schema(description = "课程描述")
    @NotBlank(message = "课程描述不能为空")
    private String description;

    @Schema(description = "课程分类ID")
    @NotNull(message = "课程分类ID不能为空")
    private Long categoryId;

    @Schema(description = "封面图", example = "https://baidu.com")
    @NotBlank(message = "封面图不能为空")
    private String thumbnailUrl;

    @Schema(description = "是否发布（0：未发布，1：已发布）")
    @NotNull(message = "是否发布（0：未发布，1：已发布）不能为空")
    private Boolean isPublished;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态（0：未删除，1：已删除）不能为空")
    private Boolean deleteState;

}
