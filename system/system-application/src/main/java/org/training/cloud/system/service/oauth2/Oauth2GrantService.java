package org.training.cloud.system.service.oauth2;

import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

import java.util.List;

/**
 * 授权
 *
 * @author wangtongzhou
 * @since 2023-05-07 20:17
 */
public interface Oauth2GrantService {


    /**
     * 获取授权码
     *
     * @param userId
     * @param userType
     * @param clientId
     * @param scopes
     * @param redirectUrl
     * @return
     */
    String grantAuthorizationCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUrl);


    /**
     * 简化模式
     *
     * @param userId
     * @param userType
     * @param clientId
     * @param scopes
     * @return
     */
    Oauth2AccessTokenVO grantImplicit(Long userId, Integer userType, String clientId, List<String> scopes);

}
