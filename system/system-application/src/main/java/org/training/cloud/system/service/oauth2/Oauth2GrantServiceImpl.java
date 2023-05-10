package org.training.cloud.system.service.oauth2;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.system.dto.oauth2.AddOauth2AccessTokenDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationCode;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.service.auth.AuthService;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

import javax.annotation.Resource;
import java.util.List;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * 授权
 *
 * @author wangtongzhou
 * @since 2023-05-07 21:05
 */
@Service
public class Oauth2GrantServiceImpl implements Oauth2GrantService {

    @Resource
    private Oauth2AuthorizationCodeService authorizationCodeService;

    @Resource
    private Oauth2TokenService oauth2TokenService;

    @Resource
    private AuthService authService;

    @Override
    public String grantAuthorizationCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUrl) {
        return authorizationCodeService.addAuthorizationCode(userId,userType,
                clientId,scopes,redirectUrl).getAuthorizationCode();
    }

    @Override
    public Oauth2AccessTokenVO grantImplicit(Long userId, Integer userType, String clientId, List<String> scopes) {
        return createAccessToken(userId,userType,clientId,scopes);
    }


    private Oauth2AccessTokenVO createAccessToken(Long userId, Integer userType, String clientId, List<String> scopes){
        AddOauth2AccessTokenDTO addOauth2AccessTokenDTO= new AddOauth2AccessTokenDTO();
        addOauth2AccessTokenDTO.setScopes(scopes);
        addOauth2AccessTokenDTO.setUserType(userType);
        addOauth2AccessTokenDTO.setUserId(userId);
        addOauth2AccessTokenDTO.setClientId(clientId);
        return oauth2TokenService.createAccessToken(addOauth2AccessTokenDTO);
    }

    @Override
    public Oauth2AccessTokenVO grantAuthorizationCodeAccessToken(String clientId, String authorizationCode, String redirectUrl, String state) {
        //参数检查
        SysOauth2AuthorizationCode sysOauth2AuthorizationCode = authorizationCodeService.userAuthorizationCode(authorizationCode);
        if (!StringUtils.equals(sysOauth2AuthorizationCode.getClientId(), clientId)){
            throw new BusinessException(OAUTH2_GRANT_CLIENT_ID_NOT_MATCH);
        }
        if (!StringUtils.equals(sysOauth2AuthorizationCode.getRedirectUrl(), redirectUrl)){
            throw new BusinessException(OAUTH2_GRANT_REDIRECT_URL_NOT_MATCH);
        }
        if (!StringUtils.equals(sysOauth2AuthorizationCode.getState(), state)){
            throw new BusinessException(OAUTH2_GRANT_STATE_NOT_MATCH);
        }
        return createAccessToken(sysOauth2AuthorizationCode.getUserId(),
                sysOauth2AuthorizationCode.getUserType(),
                sysOauth2AuthorizationCode.getClientId(),
                sysOauth2AuthorizationCode.getScopes());
    }

    @Override
    public Oauth2AccessTokenVO grantPassWord(String username, String password, String clientId, List<String> scopes) {
        SysUser sysUser =authService.authenticate(username,password);
        Assert.notNull(sysUser, "用户不能为空");
        return createAccessToken(sysUser.getId(),sysUser.getUserType(), clientId,scopes);
    }

    @Override
    public Oauth2AccessTokenVO grantRefreshAccessToken(String refreshToken, String clientId) {
        return oauth2TokenService.refreshAccessToken(refreshToken, clientId);
    }

    @Override
    public Oauth2AccessTokenVO grantClientCredentials(String refreshToken, String clientId) {
        //目前暂时不考虑做实现
        return null;
    }
}
