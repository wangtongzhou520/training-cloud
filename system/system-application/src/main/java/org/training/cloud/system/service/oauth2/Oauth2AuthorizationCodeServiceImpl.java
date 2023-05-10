package org.training.cloud.system.service.oauth2;

import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.utils.date.DateUtils;
import org.training.cloud.system.dao.oauth2.Oauth2AuthorizationCodeMapper;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationCode;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.OAUTH2_AUTHORIZATION_CODE_NOT_EXISTS;
import static org.training.cloud.system.constant.SystemExceptionEnumConstants.OAUTH2_AUTHORIZATION_CODE_NOT_EXPIRED;

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

    @Override
    public SysOauth2AuthorizationCode userAuthorizationCode(String authorizationCode) {
        //检查授权码是是否存在
        SysOauth2AuthorizationCode sysOauth2AuthorizationCode=
                authorizationCodeMapper.queryByCode(authorizationCode);
        if (Objects.isNull(sysOauth2AuthorizationCode)){
            throw new BusinessException(OAUTH2_AUTHORIZATION_CODE_NOT_EXISTS);
        }
        //检查授权码是否过期
        if (DateUtils.isExpired(sysOauth2AuthorizationCode.getExpiresTime())){
            throw new BusinessException(OAUTH2_AUTHORIZATION_CODE_NOT_EXPIRED);

        }
        //删除授权码
        authorizationCodeMapper.deleteById(sysOauth2AuthorizationCode.getId());
        return sysOauth2AuthorizationCode;
    }
}
