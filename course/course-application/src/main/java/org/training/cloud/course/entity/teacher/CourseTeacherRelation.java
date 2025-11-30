package org.training.cloud.course.entity.teacher;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName(value = "course_teacher_relation", autoResultMap = true)
@Accessors(chain = true)
public class CourseTeacherRelation implements Serializable {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 课程ID
     */
    private Long courseId;
    /**
     * 讲师ID
     */
    private Long teacherId;
    /**
     * 讲师角色（MAIN-主讲，ASSISTANT-助教）
     */
    private String teacherRole;
    /**
     * 收益分成比例（0-1）
     */
    private BigDecimal revenueShareRate;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtModified;

}
