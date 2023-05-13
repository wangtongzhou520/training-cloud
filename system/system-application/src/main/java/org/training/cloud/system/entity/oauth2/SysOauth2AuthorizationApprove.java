package org.training.cloud.system.entity.oauth2;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

import java.time.LocalDateTime;

/**
 * Oauth2审批表
 *
 * @author wangtongzhou
 * @since 2023-05-04 21:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oauth2_authorization_approve", autoResultMap = true)
@Accessors(chain = true)
public class SysOauth2AuthorizationApprove extends BaseDO {

    @TableId(type = IdType.AUTO)
    /**
     * 自增编号
     */
    private Long id;

    /**
     * 角色id
     */
    private Long userId;

    /**
     * 用户类型 0 c端 1 管理端
     */
    private int userType;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 授权范围
     */
    private String scope;

    /**
     * 是否接受
     */
    private Boolean approveState;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

}
