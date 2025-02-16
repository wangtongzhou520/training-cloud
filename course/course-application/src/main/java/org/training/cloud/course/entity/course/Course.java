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
     * 课程描述
     */
    private String description;
    /**
     * 课程分类ID
     */
    private Long categoryId;
    /**
     * 封面图
     */
    private String thumbnailUrl;
    /**
     * 是否发布（0：未发布，1：已发布）
     */
    private Boolean isPublished;

    /**
     * 章节状态(0: 无章节 1: 存在章节)
     */
    private Integer chapterState;

}







