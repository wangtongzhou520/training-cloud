package org.training.cloud.course.entity.course;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "course_lesson", autoResultMap = true)
@Accessors(chain = true)
public class Lesson extends BaseDO {
    /**
     * 课时ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 课时名称
     */
    private String lessonName;
    /**
     * 章节ID
     */
    private Long chapterId;
    /**
     * 排序序号
     */
    private Integer sort;
    /**
     * 课时URL
     */
    private String lessonUrl;
    /**
     * 课程ID（冗余字段，便于查询）
     */
    private Long courseId;
    /**
     * 课时类型（VIDEO-视频，LIVE-直播，DOC-文档，AUDIO-音频）
     */
    private String lessonType;
    /**
     * 课时时长（单位：秒）
     */
    private Integer lessonDuration;
    /**
     * 是否免费试听（0-否，1-是）
     */
    private Boolean isFree;
    /**
     * 是否可预览（0-否，1-是）
     */
    private Boolean isPreview;
    /**
     * 视频清晰度（SD-标清，HD-高清，FHD-超清，4K-4K）
     */
    private String videoQuality;
    /**
     * 文件大小（单位：字节）
     */
    private Long fileSize;
    /**
     * 学习人数统计
     */
    private Integer learnCount;
    /**
     * 平均学习进度（0-100）
     */
    private Integer avgLearnProgress;

}







