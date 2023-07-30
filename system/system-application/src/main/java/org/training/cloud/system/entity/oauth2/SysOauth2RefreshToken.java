package org.training.cloud.system.entity.oauth2;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 刷新token
 *
 * @author wangtongzhou
 * @since 2020-09-18 12:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "sys_oauth2_refresh_token", autoResultMap = true)
public class SysOauth2RefreshToken extends BaseDO {

    /**
     * 编号，刷新令牌
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 用户类型 0 c端 1 管理端
     */
    private Integer userType;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;

    /**
     * 授权范围
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;



}
