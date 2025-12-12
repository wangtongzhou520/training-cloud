package org.training.cloud.course.entity.teacher;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "teacher_info", autoResultMap = true)
@Accessors(chain = true)
public class TeacherInfo extends BaseDO {
    /**
     * 讲师ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID（关联用户表）
     */
    private Long userId;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 职称/头衔
     */
    private String teacherTitle;
    /**
     * 头像URL
     */
    private String avatarUrl;
    /**
     * 个人简介
     */
    private String introduction;
    /**
     * 擅长领域（多个用逗号分隔）
     */
    private String expertiseArea;
    /**
     * 累计学生数
     */
    private Integer totalStudentCount;
    /**
     * 课程数量
     */
    private Integer totalCourseCount;
    /**
     * 平均评分
     */
    private BigDecimal averageRating;
    /**
     * 累计收益（元）
     */
    private BigDecimal totalRevenue;
    /**
     * 是否认证讲师（0-否，1-是）
     */
    private Boolean isCertified;
    /**
     * 认证信息
     */
    private String certificationInfo;
    /**
     * 状态（PENDING-待审核，APPROVED-已通过，REJECTED-已拒绝，DISABLED-已禁用）
     */
    private String status;
    /**
     * 审核意见
     */
    private String auditOpinion;

}
