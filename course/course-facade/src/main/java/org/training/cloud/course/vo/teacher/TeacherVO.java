package org.training.cloud.course.vo.teacher;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Schema(description = "讲师信息返回")
public class TeacherVO implements Serializable {

    @Schema(description = "讲师ID")
    private Long id;

    @Schema(description = "用户ID（关联用户表）")
    private Long userId;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "职称/头衔")
    private String teacherTitle;

    @Schema(description = "头像URL")
    private String avatarUrl;

    @Schema(description = "个人简介")
    private String introduction;

    @Schema(description = "擅长领域（多个用逗号分隔）")
    private String expertiseArea;

    @Schema(description = "累计学生数")
    private Integer totalStudentCount;

    @Schema(description = "课程数量")
    private Integer totalCourseCount;

    @Schema(description = "平均评分")
    private BigDecimal averageRating;

    @Schema(description = "累计收益（元）")
    private BigDecimal totalRevenue;

    @Schema(description = "是否认证讲师（0-否，1-是）")
    private Boolean isCertified;

    @Schema(description = "认证信息")
    private String certificationInfo;

    @Schema(description = "状态（PENDING-待审核，APPROVED-已通过，REJECTED-已拒绝，DISABLED-已禁用）")
    private String status;

    @Schema(description = "审核意见")
    private String auditOpinion;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;

}
