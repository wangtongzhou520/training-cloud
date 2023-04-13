package org.training.cloud.system.dao.oauth2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;

/**
 * OAuth2 AccessToken服务
 *
 * @author wangtongzhou
 * @since 2020-09-18 13:47
 */
@Mapper
public interface OAuth2AccessTokenMapper extends BaseMapper<SysOauth2AccessToken> {

    /**
     * 通过刷新token删除访问token
     *
     * @param refreshToken refreshToken
     * @return 行数
     */
    int deleteByRefreshToken(String refreshToken);

    /**
     * 通过用户信息删除userId
     *
     * @param userId userId
     * @return 行数
     */
    int deleteByUserId(Long userId);
}
