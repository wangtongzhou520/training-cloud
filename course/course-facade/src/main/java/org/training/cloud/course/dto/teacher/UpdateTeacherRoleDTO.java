package org.training.cloud.course.dto.teacher;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Schema(description = "更新讲师角色")
public class UpdateTeacherRoleDTO implements Serializable {

    @Schema(description = "课程ID")
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    @Schema(description = "讲师ID")
    @NotNull(message = "讲师ID不能为空")
    private Long teacherId;

    @Schema(description = "讲师角色（MAIN-主讲，ASSISTANT-助教）")
    @NotBlank(message = "讲师角色不能为空")
    private String teacherRole;

    @Schema(description = "收益分成比例（0-1）")
    private BigDecimal revenueShareRate;

}
