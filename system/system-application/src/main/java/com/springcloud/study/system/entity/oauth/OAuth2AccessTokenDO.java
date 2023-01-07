package com.springcloud.study.system.entity.oauth;

import com.baomidou.mybatisplus.annotation.TableName;
import com.springcloud.study.entity.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * oauth2 token
 *
 * @author wangtongzhou
 * @since 2020-09-18 12:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "oauth2_access_token")
public class OAuth2AccessTokenDO extends BaseDO {
    /**
     * 访问令牌
     */
    private String id;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 刷新令牌
     */
    private String refreshToken;

    /**
     * 过期时间
     */
    private Date expiresTime;

    /**
     * 创建者
     */
    private String createOperator;

    /**
     * 修改者
     */
    private String modifiedOperator;

    /**
     * 修改者ip
     */
    private String modifiedOperatorIp;

    /**
     * 是否删除
     */
    private String deleted;
}
