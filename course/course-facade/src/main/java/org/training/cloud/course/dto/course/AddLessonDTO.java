package org.training.cloud.course.dto.course;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@Schema(description = "课程内容新增")
public class AddLessonDTO implements Serializable {

    @Schema(description = "课时名称")
    @NotBlank(message = "课时名称不能为空")
    private String lessonName;

    @Schema(description = "章节ID")
    @NotNull(message = "章节ID不能为空")
    private Long chapterId;

    @Schema(description = "排序序号")
    @NotNull(message = "排序序号不能为空")
    private Integer sort;

    @Schema(description = "课时URL", example = "https://baidu.com")
    @NotBlank(message = "课时URL不能为空")
    private String lessonUrl;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态（0：未删除，1：已删除）不能为空")
    private Boolean deleteState;

}
