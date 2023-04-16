package org.training.cloud.system.service.oauth2;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.oauth2.AddOauth2AccessTokenDTO;
import org.training.cloud.system.dto.oauth2.Oauth2AccessTokenDTO;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

/**
 * oauth 服务
 * Spring Security OAuth2 实现过于复杂于是乎自己重写一套算了
 *
 * @author wangtongzhou
 * @since 2020-09-17 20:35
 */
public interface Oauth2TokenService {

    /**
     * 创建token
     *
     * @param addOauth2AccessTokenDTO
     * @return
     */
    Oauth2AccessTokenVO createAccessToken(AddOauth2AccessTokenDTO addOauth2AccessTokenDTO);

    /**
     * 检查token合法性
     *
     * @param accessToken
     * @return
     */
    Oauth2AccessTokenVO checkAccessToken(String accessToken);

    /**
     * 刷新token
     *
     * @param refreshToken
     * @param clientId
     * @return
     */
    Oauth2AccessTokenVO refreshAccessToken(String refreshToken, String clientId);


    /**
     * 分页查询Token
     *
     * @param oauth2AccessTokenDTO
     * @return
     */
    PageResponse<Oauth2AccessTokenVO> pageOauth2AccessToken(Oauth2AccessTokenDTO oauth2AccessTokenDTO);


    /**
     * 删除token
     *
     * @param accessToken
     */
    void removeToken(String accessToken);
}
