package com.springcloud.study.system.service.oauth;

import com.springcloud.study.common.core.exception.ServerException;
import com.springcloud.study.system.bo.oauth.OAuth2AccessTokenBO;
import com.springcloud.study.system.convert.oauth.SysOAuthConvert;
import com.springcloud.study.system.dao.oauth.OAuth2AccessTokenMapper;
import com.springcloud.study.system.dao.oauth.OAuth2RefreshTokenMapper;
import com.springcloud.study.system.entity.oauth.OAuth2AccessTokenDO;
import com.springcloud.study.system.entity.oauth.OAuth2RefreshTokenDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.springcloud.study.system.constant.SystemExceptionEnumConstants.*;

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
    public OAuth2AccessTokenBO createAccessToken(Long userId, String userIp) {
        //插入刷新令牌
        OAuth2RefreshTokenDO oAuth2RefreshToken = createOAuth2RefreshToken(userId, userIp);
        //插入access_token
        OAuth2AccessTokenDO oAuth2AccessTokenDO = createOAuth2AccessToken(oAuth2RefreshToken);
        return SysOAuthConvert.INSTANCE.convert(oAuth2AccessTokenDO);
    }

    /**
     * 构建刷新令牌
     *
     * @param userId userId userId
     * @param userIp userIp userIp
     * @return oAuth2RefreshTokenDO
     */
    private OAuth2RefreshTokenDO createOAuth2RefreshToken(Long userId, String userIp) {
        OAuth2RefreshTokenDO oAuth2RefreshTokenDO = new OAuth2RefreshTokenDO();
        oAuth2RefreshTokenDO.setCreateOperator("")
                .setId(UUID.randomUUID().toString())
                .setExpiresTime(new Date(System.currentTimeMillis() + refreshTokenExpireTimeMillis))
                .setModifiedOperator("")
                .setModifiedOperatorIp(userIp)
                .setUserId(userId);
        oAuth2RefreshTokenMapper.insert(oAuth2RefreshTokenDO);
        return oAuth2RefreshTokenDO;
    }


    /**
     * 创建令牌
     *
     * @param oAuth2RefreshTokenDO oAuth2RefreshTokenDO
     * @return oAuth2AccessTokenDO
     */
    private OAuth2AccessTokenDO createOAuth2AccessToken(OAuth2RefreshTokenDO oAuth2RefreshTokenDO) {
        OAuth2AccessTokenDO oAuth2AccessTokenDO = new OAuth2AccessTokenDO();
        oAuth2AccessTokenDO.setCreateOperator("")
                .setId(UUID.randomUUID().toString())
                .setExpiresTime(new Date(System.currentTimeMillis() + accessTokenExpireTimeMillis))
                .setModifiedOperator("")
                .setRefreshToken(oAuth2RefreshTokenDO.getId())
                .setModifiedOperatorIp(oAuth2RefreshTokenDO.getModifiedOperatorIp())
                .setUserId(oAuth2AccessTokenDO.getUserId())
                .setCreateOperator("");
        oAuth2AccessTokenMapper.insert(oAuth2AccessTokenDO);
        return oAuth2AccessTokenDO;

    }


    @Override
    public OAuth2AccessTokenBO checkAccessToken(String accessToken) {
        //查询token
        OAuth2AccessTokenDO oAuth2AccessTokenDO =
                oAuth2AccessTokenMapper.selectById(accessToken);
        //检查token存在不存在
        if (Objects.isNull(oAuth2AccessTokenDO)) {
            throw new ServerException(OAUTH2_ACCESS_TOKEN_NOT_FOUND);
        }
        //检查token过期时间
        if (oAuth2AccessTokenDO.getExpiresTime().getTime() < System.currentTimeMillis()) {
            throw new ServerException(OAUTH2_ACCESS_TOKEN_NOT_EXPIRED);
        }
        //返回访问令牌
        return SysOAuthConvert.INSTANCE.convert(oAuth2AccessTokenDO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OAuth2AccessTokenBO refreshAccessToken(String refreshToken, String userIp) {
        //获取刷新token
        OAuth2RefreshTokenDO auth2RefreshTokenDO =
                oAuth2RefreshTokenMapper.selectById(refreshToken);
        //校验刷新token存在不存在
        if (Objects.nonNull(auth2RefreshTokenDO)){
            throw new ServerException(OAUTH2_REFRESH_TOKEN_NOT_FOUND);
        }
        //检验刷新token是否过期
        if (auth2RefreshTokenDO.getExpiresTime().getTime()<System.currentTimeMillis()){
            throw new ServerException(OAUTH2_REFRESH_TOKEN_NOT_EXPIRED);
        }
        //如果不过期则删除之前访问令牌重新创建令牌
        oAuth2AccessTokenMapper.deleteByRefreshToken(refreshToken);
        OAuth2AccessTokenDO oAuth2AccessTokenDO=createOAuth2AccessToken(auth2RefreshTokenDO);
        return SysOAuthConvert.INSTANCE.convert(oAuth2AccessTokenDO);
    }

    @Override
    public void removeToken(Long userId) {
        oAuth2AccessTokenMapper.deleteByUserId(userId);
        oAuth2RefreshTokenMapper.deleteByUserId(userId);
    }
}
