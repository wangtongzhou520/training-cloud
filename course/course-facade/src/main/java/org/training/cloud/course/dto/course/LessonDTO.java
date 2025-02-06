package org.training.cloud.course.dto.course;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;


@Data
@Accessors(chain = true)
@Schema(description = "课程内容分页查询")
public class LessonDTO extends PageParam {

    @Schema(description = "课时ID" )
    private Long id;

    @Schema(description = "课时名称" )
    private String lessonName;

    @Schema(description = "章节ID" )
    private Long chapterId;

    @Schema(description = "排序序号" )
    private Integer sort;

    @Schema(description = "课时URL" , example = "https://baidu.com")
    private String lessonUrl;

    @Schema(description = "删除状态（0：未删除，1：已删除）" )
    private Boolean deleteState;



}


