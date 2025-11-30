package org.training.cloud.course.vo.teacher;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Schema(description = "课程讲师信息返回")
public class CourseTeacherVO implements Serializable {

    @Schema(description = "关联ID")
    private Long id;

    @Schema(description = "课程ID")
    private Long courseId;

    @Schema(description = "讲师ID")
    private Long teacherId;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "职称/头衔")
    private String teacherTitle;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "个人简介")
    private String introduction;

    @Schema(description = "讲师角色（MAIN-主讲，ASSISTANT-助教）")
    private String teacherRole;

    @Schema(description = "收益分成比例（0-1）")
    private BigDecimal revenueShareRate;

    @Schema(description = "排序")
    private Integer sort;

}
