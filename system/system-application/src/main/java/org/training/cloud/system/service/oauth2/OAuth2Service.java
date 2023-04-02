package org.training.cloud.system.service.oauth2;

import org.training.cloud.system.vo.oauth2.OAuth2AccessTokenVO;

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
    OAuth2AccessTokenVO createAccessToken(Long userId, String userIp);

    /**
     * 检查token
     *
     * @param accessToken accessToken
     * @return 用户相关的token信息
     */
    OAuth2AccessTokenVO checkAccessToken(String accessToken);

    /**
     * 刷新token
     *
     * @param refreshToken refreshToken
     * @param userIp       userIp
     * @return 用户相关的token信息
     */
    OAuth2AccessTokenVO refreshAccessToken(String refreshToken, String userIp);


    /**
     * 删除token
     *
     * @param userId userId
     */
    void removeToken(Long userId);
}
