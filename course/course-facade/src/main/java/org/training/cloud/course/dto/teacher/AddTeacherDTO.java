package org.training.cloud.course.dto.teacher;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(description = "讲师新增")
public class AddTeacherDTO implements Serializable {

    @Schema(description = "用户ID（关联用户表）")
    @NotNull(message = "用户ID不能为空")
    private Long userId;

    @Schema(description = "真实姓名")
    @NotBlank(message = "真实姓名不能为空")
    private String realName;

    @Schema(description = "职称/头衔")
    private String teacherTitle;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "个人简介")
    private String introduction;

    @Schema(description = "擅长领域（多个用逗号分隔）")
    private String expertiseArea;

    @Schema(description = "是否认证讲师（0-否，1-是）")
    private Boolean isCertified;

    @Schema(description = "认证信息")
    private String certificationInfo;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态不能为空")
    private Boolean deleteState;

}
