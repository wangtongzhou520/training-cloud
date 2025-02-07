package org.training.cloud.course.vo.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;


@Data
@Accessors(chain = true)
@Schema(description = "课程章节返回信息")
public class ChapterVO implements Serializable {

    @Schema(description = "章节ID")
    private Long id;

    @Schema(description = "章节名称")
    private String chapterName;

    @Schema(description = "课程ID")
    private Long courseId;

    @Schema(description = "排序序号")
    private Integer sort;


    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;


}
