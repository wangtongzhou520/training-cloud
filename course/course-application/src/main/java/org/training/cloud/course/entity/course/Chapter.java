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
@TableName(value = "course_chapter", autoResultMap = true)
@Accessors(chain = true)
public class Chapter extends BaseDO {
    /**
     * 章节ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 章节名称
     */
    private String chapterName;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 排序序号
     */
    private Integer sort;

}







