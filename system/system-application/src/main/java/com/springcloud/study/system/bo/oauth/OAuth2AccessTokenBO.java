package com.springcloud.study.system.bo.oauth;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * OAuth2AccessTokenBO
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
@Data
@Accessors(chain = true)
public class OAuth2AccessTokenBO {

    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 过期时间
     */
    private Date expiresTime;

}
