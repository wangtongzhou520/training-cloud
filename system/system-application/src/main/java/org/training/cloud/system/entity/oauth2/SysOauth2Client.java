package org.training.cloud.system.entity.oauth2;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.web.mybatis.dao.BaseDO;

import java.util.List;

/**
 * 授权客户端管理
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-02 09:48
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oauth2_client")
@Accessors(chain = true)
public class SysOauth2Client extends BaseDO {

    @TableId
    /**
     * 唯一键
     */
    private Long id;

    /**
     * 客户端id
     */
    private String clientId;

    /**
     * 客户端密钥
     */
    private String clientSecret;

    /**
     * 客户端名称
     */
    private String clientName;

    /**
     * 头像地址
     */
    private String clientLogo;

    /**
     * 应用描述
     */
    private String clientDescription;

    /**
     * 授权方式
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> grantTypes;

    /**
     * 重定向地址
     */
    private String redirectUrl;

    /**
     * 授权范围
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private List<String> scopes;

    /**
     * 授权码有效期
     */
    private Long accessTokenValiditySeconds;

    /**
     * 授权码有效期
     */
    private Long refreshTokenValiditySeconds;

    /**
     * 状态，0：正常，1：删除
     */
    private int status;
}
