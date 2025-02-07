package org.training.cloud.course.dto.course;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Schema(description = "课程章节修改")
public class ModifyChapterDTO implements Serializable {

    @Schema(description = "章节ID")
    private Long id;

    @Schema(description = "章节名称")
    @NotBlank(message = "章节名称不能为空")
    private String chapterName;

    @Schema(description = "课程ID")
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    @Schema(description = "排序序号")
    @NotNull(message = "排序序号不能为空")
    private Integer sort;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态（0：未删除，1：已删除）不能为空")
    private Boolean deleteState;

}
