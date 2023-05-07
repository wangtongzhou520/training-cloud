package org.training.cloud.system.service.oauth2;

import org.springframework.stereotype.Service;
import org.training.cloud.system.dto.oauth2.AddOauth2AccessTokenDTO;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

import javax.annotation.Resource;
import java.util.List;

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

    @Override
    public String grantAuthorizationCode(Long userId, Integer userType, String clientId, List<String> scopes, String redirectUrl) {
        return authorizationCodeService.addAuthorizationCode(userId,userType,
                clientId,scopes,redirectUrl).getAuthorizationCode();
    }

    @Override
    public Oauth2AccessTokenVO grantImplicit(Long userId, Integer userType, String clientId, List<String> scopes) {
        AddOauth2AccessTokenDTO addOauth2AccessTokenDTO= new AddOauth2AccessTokenDTO();
        addOauth2AccessTokenDTO.setScopes(scopes);
        addOauth2AccessTokenDTO.setUserType(userType);
        addOauth2AccessTokenDTO.setUserId(userId);
        addOauth2AccessTokenDTO.setClientId(clientId);
        return oauth2TokenService.createAccessToken(addOauth2AccessTokenDTO);
    }
}
