package com.springcloud.study.system.service.oauth;

import com.springcloud.study.system.bo.oauth.OAuth2AccessTokenBO;

/**
 * oauth 服务
 * Spring Security OAuth2 实现过于复杂于是乎自己重写一套算了
 *
 * @author wangtongzhou
 * @since 2020-09-17 20:35
 */
public interface OAuth2Service {

    /**
     * 创建token
     *
     * @param userId userId
     * @param userIp userIp
     * @return 用户相关的token信息
     */
    OAuth2AccessTokenBO createAccessToken(Long userId, String userIp);

    /**
     * 检查token
     *
     * @param accessToken accessToken
     * @return 用户相关的token信息
     */
    OAuth2AccessTokenBO checkAccessToken(String accessToken);

    /**
     * 刷新token
     *
     * @param refreshToken refreshToken
     * @param userIp       userIp
     * @return 用户相关的token信息
     */
    OAuth2AccessTokenBO refreshAccessToken(String refreshToken, String userIp);


    /**
     * 删除token
     *
     * @param userId userId
     */
    void removeToken(Long userId);
}
