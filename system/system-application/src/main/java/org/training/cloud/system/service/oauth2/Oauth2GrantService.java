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


    /**
     * 授权码模式
     *
     * @param clientId
     * @param authorizationCode
     * @param redirectUrl
     * @param state
     * @return
     */
    Oauth2AccessTokenVO grantAuthorizationCodeAccessToken(String clientId, String authorizationCode,
                                                          String redirectUrl, String state);


    /**
     * 密码模式
     *
     * @param username
     * @param password
     * @param clientId
     * @param scopes
     * @return
     */
    Oauth2AccessTokenVO grantPassWord(String username, String password,
                                      String clientId, List<String> scopes);


    /**
     * 刷新令牌
     *
     * @param refreshToken
     * @param clientId
     * @return
     */
    Oauth2AccessTokenVO grantRefreshAccessToken(String refreshToken, String clientId);


    /**
     * 客户端模式
     *
     * @param refreshToken
     * @param clientId
     * @return
     */
    Oauth2AccessTokenVO grantClientCredentials(String refreshToken, String clientId);


}
