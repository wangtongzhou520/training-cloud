package org.training.cloud.course.dto.course;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;


@Data
@Accessors(chain = true)
@Schema(description = "课程分页查询")
public class CourseDTO extends PageParam {

    @Schema(description = "课程ID" )
    private Long id;

    @Schema(description = "课程名称" )
    private String courseName;

    @Schema(description = "课程描述" )
    private String description;

    @Schema(description = "课程分类ID" )
    private Long categoryId;

    @Schema(description = "封面图" , example = "https://baidu.com")
    private String thumbnailUrl;

    @Schema(description = "是否发布（0：未发布，1：已发布）" )
    private Boolean isPublished;

    @Schema(description = "删除状态（0：未删除，1：已删除）" )
    private Boolean deleteState;



}


