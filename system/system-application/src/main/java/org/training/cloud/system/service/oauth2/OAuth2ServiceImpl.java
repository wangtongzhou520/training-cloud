package org.training.cloud.system.service.oauth2;

import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.exception.ServerException;
import org.training.cloud.system.convert.oauth2.SysOAuthConvert;
import org.training.cloud.system.dao.oauth2.OAuth2AccessTokenMapper;
import org.training.cloud.system.dao.oauth2.OAuth2RefreshTokenMapper;
import org.training.cloud.system.entity.oauth2.Oauth2AccessToken;
import org.training.cloud.system.entity.oauth2.Oauth2RefreshToken;
import org.training.cloud.system.vo.oauth2.OAuth2AccessTokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * OAuth 相关服务
 *
 * @author wangtongzhou
 * @since 2020-09-18 11:59
 */
public class OAuth2ServiceImpl implements OAuth2Service {

    @Value("${refresh.token.expire.time.millis}")
    private int refreshTokenExpireTimeMillis;

    @Value("${access.token.expire.time.millis}")
    private int accessTokenExpireTimeMillis;


    @Autowired
    private OAuth2RefreshTokenMapper oAuth2RefreshTokenMapper;

    @Autowired
    private OAuth2AccessTokenMapper oAuth2AccessTokenMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2AccessTokenVO createAccessToken(Long userId, String userIp) {
        //插入刷新令牌
        Oauth2RefreshToken oAuth2RefreshToken = createOAuth2RefreshToken(userId, userIp);
        //插入access_token
        Oauth2AccessToken oAuth2AccessToken = createOAuth2AccessToken(oAuth2RefreshToken);
        return SysOAuthConvert.INSTANCE.convert(oAuth2AccessToken);
    }

    /**
     * 构建刷新令牌
     *
     * @param userId userId userId
     * @param userIp userIp userIp
     * @return oAuth2RefreshTokenDO
     */
    private Oauth2RefreshToken createOAuth2RefreshToken(Long userId, String userIp) {
        Oauth2RefreshToken oAuth2RefreshToken = new Oauth2RefreshToken();
        oAuth2RefreshToken.setCreateOperator("")
                .setId(UUID.randomUUID().toString())
                .setExpiresTime(new Date(System.currentTimeMillis() + refreshTokenExpireTimeMillis))
                .setModifiedOperator("")
                .setModifiedOperatorIp(userIp)
                .setUserId(userId);
        oAuth2RefreshTokenMapper.insert(oAuth2RefreshToken);
        return oAuth2RefreshToken;
    }


    /**
     * 创建令牌
     *
     * @param oAuth2RefreshToken oAuth2RefreshTokenDO
     * @return oAuth2AccessTokenDO
     */
    private Oauth2AccessToken createOAuth2AccessToken(Oauth2RefreshToken oAuth2RefreshToken) {
        Oauth2AccessToken oAuth2AccessToken = new Oauth2AccessToken();
        oAuth2AccessToken.setCreateOperator("")
                .setId(UUID.randomUUID().toString())
                .setExpiresTime(new Date(System.currentTimeMillis() + accessTokenExpireTimeMillis))
                .setModifiedOperator("")
                .setRefreshToken(oAuth2RefreshToken.getId())
                .setModifiedOperatorIp(oAuth2RefreshToken.getModifiedOperatorIp())
                .setUserId(oAuth2AccessToken.getUserId())
                .setCreateOperator("");
        oAuth2AccessTokenMapper.insert(oAuth2AccessToken);
        return oAuth2AccessToken;

    }


    @Override
    public OAuth2AccessTokenVO checkAccessToken(String accessToken) {
        //查询token
        Oauth2AccessToken oAuth2AccessToken =
                oAuth2AccessTokenMapper.selectById(accessToken);
        //检查token存在不存在
        if (Objects.isNull(oAuth2AccessToken)) {
            throw new BusinessException(OAUTH2_ACCESS_TOKEN_NOT_FOUND);
        }
        //检查token过期时间
        if (oAuth2AccessToken.getExpiresTime().getTime() < System.currentTimeMillis()) {
            throw new ServerException(OAUTH2_ACCESS_TOKEN_NOT_EXPIRED);
        }
        //返回访问令牌
        return SysOAuthConvert.INSTANCE.convert(oAuth2AccessToken);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2AccessTokenVO refreshAccessToken(String refreshToken, String userIp) {
        //获取刷新token
        Oauth2RefreshToken auth2RefreshTokenDO =
                oAuth2RefreshTokenMapper.selectById(refreshToken);
        //校验刷新token存在不存在
        if (Objects.nonNull(auth2RefreshTokenDO)) {
            throw new ServerException(OAUTH2_REFRESH_TOKEN_NOT_FOUND);
        }
        //检验刷新token是否过期
        if (auth2RefreshTokenDO.getExpiresTime().getTime() < System.currentTimeMillis()) {
            throw new ServerException(OAUTH2_REFRESH_TOKEN_NOT_EXPIRED);
        }
        //如果不过期则删除之前访问令牌重新创建令牌
        oAuth2AccessTokenMapper.deleteByRefreshToken(refreshToken);
        Oauth2AccessToken oAuth2AccessToken = createOAuth2AccessToken(auth2RefreshTokenDO);
        return SysOAuthConvert.INSTANCE.convert(oAuth2AccessToken);
    }

    @Override
    public void removeToken(Long userId) {
        oAuth2AccessTokenMapper.deleteByUserId(userId);
        oAuth2RefreshTokenMapper.deleteByUserId(userId);
    }
}
