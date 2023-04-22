package org.training.cloud.system.dao.redis.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.utils.date.DateUtils;
import org.training.cloud.common.redis.core.RedisUtils;
import org.training.cloud.system.constant.SystemRedisKeyConstants;
import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;

import java.time.LocalDateTime;

/**
 * 授权令牌缓存
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-16 21:49
 */
@Service
public class Oauth2AccessTokenCacheDAO {

    @Autowired
    private RedisUtils redisUtils;


    public void set(SysOauth2AccessToken accessToken) {
        String key = SystemRedisKeyConstants.OAUTH2_ACCESS_TOKEN + accessToken.getAccessToken();
        long time = DateUtils.getSecondsBetween(LocalDateTime.now(), accessToken.getExpiresTime());
        redisUtils.set(key,accessToken,time);
    }

    public SysOauth2AccessToken get(String accessToken){
        String key = SystemRedisKeyConstants.OAUTH2_ACCESS_TOKEN + accessToken;
        return (SysOauth2AccessToken) redisUtils.get(key);
    }
}
