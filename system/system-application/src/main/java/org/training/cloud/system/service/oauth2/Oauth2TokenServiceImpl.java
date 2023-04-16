package org.training.cloud.system.service.oauth2;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.constant.UserExceptionCode;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.system.convert.oauth2.SysOAuthConvert;
import org.training.cloud.system.dao.oauth2.Oauth2AccessTokenMapper;
import org.training.cloud.system.dao.oauth2.Oauth2RefreshTokenMapper;
import org.training.cloud.system.dto.oauth2.AddOauth2AccessTokenDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;
import org.training.cloud.system.entity.oauth2.SysOauth2RefreshToken;
import org.training.cloud.system.vo.oauth2.OAuth2AccessTokenVO;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * OAuth 相关服务
 *
 * @author wangtongzhou
 * @since 2020-09-18 11:59
 */
@Service
public class Oauth2TokenServiceImpl implements Oauth2TokenService {

    @Autowired
    private Oauth2RefreshTokenMapper oauth2RefreshTokenMapper;

    @Autowired
    private Oauth2AccessTokenMapper oauth2AccessTokenMapper;

    @Autowired
    private Oauth2ClientService oauth2ClientService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2AccessTokenVO createAccessToken(AddOauth2AccessTokenDTO addOauth2AccessTokenDTO) {
        //检查授权客户端
        SysOauth2Client sysOauth2Client = oauth2ClientService.queryOauth2ClientByClientId(addOauth2AccessTokenDTO.getClientId());
        //插入刷新令牌
        SysOauth2RefreshToken oauth2RefreshToken =
                createOAuth2RefreshToken(sysOauth2Client, addOauth2AccessTokenDTO);
        //插入access_token
        SysOauth2AccessToken auth2AccessToken =
                createOAuth2AccessToken(oauth2RefreshToken, sysOauth2Client);
        return SysOAuthConvert.INSTANCE.convert(auth2AccessToken);
    }

    /**
     * 构建刷新令牌
     *
     * @param client
     * @param addOauth2AccessTokenDTO
     * @return
     */
    private SysOauth2RefreshToken createOAuth2RefreshToken(SysOauth2Client client, AddOauth2AccessTokenDTO addOauth2AccessTokenDTO) {
        SysOauth2RefreshToken oauth2RefreshToken = new SysOauth2RefreshToken();
        oauth2RefreshToken
                .setExpiresTime(DateUtils.addSeconds(new Date(), client.getRefreshTokenValiditySeconds().intValue()))
                .setRefreshToken(UUID.randomUUID().toString())
                .setUserId(addOauth2AccessTokenDTO.getUserId())
                .setUserType(addOauth2AccessTokenDTO.getUserType())
                .setClientId(addOauth2AccessTokenDTO.getClientId())
                .setScopes(client.getScopes());
        oauth2RefreshTokenMapper.insert(oauth2RefreshToken);
        return oauth2RefreshToken;
    }


    /**
     * 创建令牌
     *
     * @param oauth2RefreshToken
     * @param client
     * @return
     */
    private SysOauth2AccessToken createOAuth2AccessToken(SysOauth2RefreshToken oauth2RefreshToken, SysOauth2Client client) {
        SysOauth2AccessToken oauth2AccessToken = new SysOauth2AccessToken();
        oauth2AccessToken
                .setExpiresTime(DateUtils.addSeconds(new Date(), client.getAccessTokenValiditySeconds().intValue()))
                .setRefreshToken(oauth2RefreshToken.getRefreshToken())
                .setAccessToken(UUID.randomUUID().toString())
                .setUserId(oauth2RefreshToken.getUserId())
                .setUserType(oauth2RefreshToken.getUserType())
                .setScopes(oauth2RefreshToken.getScopes())
                .setClientId(oauth2RefreshToken.getClientId());
        oauth2AccessTokenMapper.insert(oauth2AccessToken);
        return oauth2AccessToken;

    }


    @Override
    public OAuth2AccessTokenVO checkAccessToken(String accessToken) {
        //查询token
        SysOauth2AccessToken oAuth2AccessTokenSys = oauth2AccessTokenMapper.queryAccessModelByAccessToken(accessToken);
        //检查token存在不存在
        if (Objects.isNull(oAuth2AccessTokenSys)) {
            throw new BusinessException(OAUTH2_ACCESS_TOKEN_NOT_FOUND);
        }
        //检查token过期时间
        if (oAuth2AccessTokenSys.getExpiresTime().getTime() < System.currentTimeMillis()) {
            throw new BusinessException(OAUTH2_ACCESS_TOKEN_NOT_EXPIRED);
        }
        //返回访问令牌
        return SysOAuthConvert.INSTANCE.convert(oAuth2AccessTokenSys);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2AccessTokenVO refreshAccessToken(String refreshToken, String clientId) {
        //获取刷新token
        SysOauth2RefreshToken auth2RefreshTokenDO =
                oauth2RefreshTokenMapper.queryRefreshByRefreshToken(refreshToken);
        //校验刷新token存在不存在
        if (Objects.isNull(auth2RefreshTokenDO)) {
            throw new BusinessException(OAUTH2_REFRESH_TOKEN_NOT_FOUND);
        }
        //检查client_id是否正确
        SysOauth2Client sysOauth2Client =
                oauth2ClientService.queryOauth2ClientByClientId(auth2RefreshTokenDO.getClientId());
        if (!StringUtils.equals(clientId, sysOauth2Client.getClientId())) {
            throw new BusinessException(UserExceptionCode.BAD_REQUEST.getCode(), "客户端参数异常");
        }
        //删除访问令牌
        List<SysOauth2AccessToken> accessTokens = oauth2AccessTokenMapper.queryAccessListByRefreshToken(refreshToken);
        if (CollectionUtils.isNotEmpty(accessTokens)) {
            oauth2AccessTokenMapper.deleteBatchIds(accessTokens.stream().map(SysOauth2AccessToken::getAccessToken).collect(Collectors.toSet()));
            //缓存补充
        }
        //检验刷新token是否过期
        if (auth2RefreshTokenDO.getExpiresTime().getTime() < System.currentTimeMillis()) {
            //如果过期删除刷新令牌抛出异常
            oauth2RefreshTokenMapper.deleteById(auth2RefreshTokenDO.getRefreshToken());
            throw new BusinessException(OAUTH2_REFRESH_TOKEN_NOT_EXPIRED);
        }
        SysOauth2AccessToken accessToken= createOAuth2AccessToken(auth2RefreshTokenDO, sysOauth2Client);
        return SysOAuthConvert.INSTANCE.convert(accessToken);
    }

    @Override
    public void removeToken(Long userId) {
//        oAuth2AccessTokenMapper.deleteByUserId(userId);
//        oAuth2RefreshTokenMapper.deleteByUserId(userId);
    }
}
