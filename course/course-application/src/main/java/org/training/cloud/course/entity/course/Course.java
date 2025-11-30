package org.training.cloud.course.entity.course;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "course", autoResultMap = true)
@Accessors(chain = true)
public class Course extends BaseDO {
    /**
     * 课程ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程副标题
     */
    private String courseSubTitle;

    /**
     * 课程描述
     */
    private String courseDescription;

    /**
     * 课程分类ID
     */
    private Long categoryId;

    /**
     * 封面图URL
     */
    private String thumbnailUrl;

    /**
     * 课程介绍视频URL
     */
    private String introVideoUrl;

    // ========== 课程状态 ==========

    /**
     * 是否发布（0：未发布，1：已发布）
     */
    private Boolean isPublished;

    /**
     * 发布时间
     */
    private LocalDateTime publishTime;

    /**
     * 章节状态(0: 无章节 1: 存在章节)
     */
    private Integer chapterState;

    // ========== 课程统计（冗余字段，定期更新） ==========

    /**
     * 学习人数
     */
    private Integer studentCount;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 收藏次数
     */
    private Integer collectCount;

    /**
     * 分享次数
     */
    private Integer shareCount;

    /**
     * 平均评分（0-5分）
     */
    private BigDecimal averageRating;

    /**
     * 评价数量
     */
    private Integer reviewCount;

    // ========== 课程属性 ==========

    /**
     * 难度级别（1-入门，2-初级，3-中级，4-高级，5-专家）
     */
    private Integer difficultyLevel;

    /**
     * 预计学习时长（分钟）
     */
    private Integer estimatedDuration;

    /**
     * 总课时数
     */
    private Integer totalLessonCount;

    /**
     * 课程语言（zh_CN-中文，en_US-英文）
     */
    private String courseLanguage;

    // ========== 课程设置 ==========

    /**
     * 是否允许下载（0-不允许，1-允许）
     */
    private Boolean allowDownload;

    /**
     * 是否允许试听（0-不允许，1-允许）
     */
    private Boolean allowTrial;

    /**
     * 可试听课时数
     */
    private Integer trialLessonCount;

}







