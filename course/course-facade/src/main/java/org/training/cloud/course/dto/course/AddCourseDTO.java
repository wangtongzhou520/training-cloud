package org.training.cloud.course.dto.course;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@Accessors(chain = true)
@Schema(description = "课程新增")
public class AddCourseDTO implements Serializable {

    @Schema(description = "课程名称")
    @NotBlank(message = "课程名称不能为空")
    private String courseName;

    @Schema(description = "课程副标题")
    private String courseSubTitle;

    @Schema(description = "课程描述")
    @NotBlank(message = "课程描述不能为空")
    private String courseDescription;

    @Schema(description = "课程分类ID")
    @NotNull(message = "课程分类ID不能为空")
    private Long categoryId;

    @Schema(description = "封面图URL", example = "https://baidu.com")
    @NotBlank(message = "封面图不能为空")
    private String thumbnailUrl;

    @Schema(description = "课程介绍视频URL")
    private String introVideoUrl;

    @Schema(description = "难度级别（1-入门，2-初级，3-中级，4-高级，5-专家）")
    private Integer difficultyLevel;

    @Schema(description = "预计学习时长（分钟）")
    private Integer estimatedDuration;

    @Schema(description = "课程语言（zh_CN-中文，en_US-英文）")
    private String courseLanguage;

    @Schema(description = "是否允许下载（0-不允许，1-允许）")
    private Boolean allowDownload;

    @Schema(description = "是否允许试听（0-不允许，1-允许）")
    private Boolean allowTrial;

    @Schema(description = "可试听课时数")
    private Integer trialLessonCount;

    @Schema(description = "是否发布（0：未发布，1：已发布）")
    @NotNull(message = "是否发布（0：未发布，1：已发布）不能为空")
    private Boolean isPublished;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态（0：未删除，1：已删除）不能为空")
    private Boolean deleteState;

}
