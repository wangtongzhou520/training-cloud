package org.training.cloud.course.dto.teacher;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(description = "讲师分页查询")
public class TeacherDTO extends PageParam implements Serializable {

    @Schema(description = "真实姓名（模糊查询）")
    private String realName;

    @Schema(description = "状态（PENDING-待审核，APPROVED-已通过，REJECTED-已拒绝，DISABLED-已禁用）")
    private String status;

    @Schema(description = "是否认证讲师（0-否，1-是）")
    private Boolean isCertified;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;

}
