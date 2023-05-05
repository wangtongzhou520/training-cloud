package org.training.cloud.system.service.oauth2;

import org.springframework.stereotype.Service;
import org.training.cloud.system.dao.oauth2.Oauth2AuthorizationCodeMapper;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationCode;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 授权码表
 *
 * @author wangtongzhou
 * @since 2023-05-05 21:41
 */
@Service
public class Oauth2AuthorizationCodeServiceImpl implements Oauth2AuthorizationCodeService {
    /**
     * 授权码的过期时间，默认3分钟
     */
    private static final Integer TIMEOUT = 3 * 60;

    @Resource
    private Oauth2AuthorizationCodeMapper authorizationCodeMapper;

    @Override
    public SysOauth2AuthorizationCode addAuthorizationCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUrl) {
        SysOauth2AuthorizationCode authorizationCode = new SysOauth2AuthorizationCode()
                .setAuthorizationCode(UUID.randomUUID().toString())
                .setClientId(clientId)
                .setRedirectUrl(redirectUrl)
                .setUserId(userId)
                .setUserType(userType)
                .setExpiresTime(LocalDateTime.now().plusSeconds(TIMEOUT))
                .setScopes(scopes);
        authorizationCodeMapper.insert(authorizationCode);
        return authorizationCode;
    }
}
