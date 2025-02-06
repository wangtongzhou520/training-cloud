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

}







