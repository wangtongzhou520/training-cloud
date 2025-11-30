package org.training.cloud.course.vo.course;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@Schema(description = "课程返回信息")
public class CourseVO implements Serializable {

    @Schema(description = "课程ID")
    private Long id;

    @Schema(description = "课程名称")
    private String courseName;

    @Schema(description = "课程副标题")
    private String courseSubTitle;

    @Schema(description = "课程描述")
    private String courseDescription;

    @Schema(description = "课程分类ID")
    private Long categoryId;

    @Schema(description = "封面图URL", example = "https://baidu.com")
    private String thumbnailUrl;

    @Schema(description = "课程介绍视频URL")
    private String introVideoUrl;

    // ========== 课程状态 ==========

    @Schema(description = "是否发布（0：未发布，1：已发布）")
    private Boolean isPublished;

    @Schema(description = "发布时间")
    private LocalDateTime publishTime;

    @Schema(description = "章节状态(0: 无章节 1: 存在章节)")
    private Integer chapterState;

    // ========== 课程统计 ==========

    @Schema(description = "学习人数")
    private Integer studentCount;

    @Schema(description = "浏览次数")
    private Integer viewCount;

    @Schema(description = "收藏次数")
    private Integer collectCount;

    @Schema(description = "分享次数")
    private Integer shareCount;

    @Schema(description = "平均评分（0-5分）")
    private BigDecimal averageRating;

    @Schema(description = "评价数量")
    private Integer reviewCount;

    // ========== 课程属性 ==========

    @Schema(description = "难度级别（1-入门，2-初级，3-中级，4-高级，5-专家）")
    private Integer difficultyLevel;

    @Schema(description = "预计学习时长（分钟）")
    private Integer estimatedDuration;

    @Schema(description = "总课时数")
    private Integer totalLessonCount;

    @Schema(description = "课程语言（zh_CN-中文，en_US-英文）")
    private String courseLanguage;

    // ========== 课程设置 ==========

    @Schema(description = "是否允许下载（0-不允许，1-允许）")
    private Boolean allowDownload;

    @Schema(description = "是否允许试听（0-不允许，1-允许）")
    private Boolean allowTrial;

    @Schema(description = "可试听课时数")
    private Integer trialLessonCount;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;

}
