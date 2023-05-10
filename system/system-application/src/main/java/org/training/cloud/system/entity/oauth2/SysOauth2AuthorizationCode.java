package org.training.cloud.system.entity.oauth2;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 授权码表
 *
 * @author wangtongzhou
 * @since 2023-05-05 21:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oauth2_authorization_code", autoResultMap = true)
@Accessors(chain = true)
public class SysOauth2AuthorizationCode extends BaseDO {

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
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;

    /**
     * 重定向地址
     */
    private String redirectUrl;

    /**
     * 授权码
     */
    private String authorizationCode;

    /**
     * 过期时间
     */
    private LocalDateTime expiresTime;


    /**
     * 状态
     */
    private String state;
}
