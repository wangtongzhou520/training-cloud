package org.training.cloud.system.service.oauth2;

import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationCode;

import java.util.List;

/**
 * 授权码表
 *
 * @author wangtongzhou 
 * @since 2023-05-05 21:41
 */
public interface Oauth2AuthorizationCodeService {


    /**
     * 新增授权码
     *
     * @param userId
     * @param userType
     * @param clientId
     * @param scopes
     * @param redirectUrl
     * @return
     */
    SysOauth2AuthorizationCode addAuthorizationCode(Long userId, Integer userType, String clientId,
                                                    List<String> scopes, String redirectUrl);

}
