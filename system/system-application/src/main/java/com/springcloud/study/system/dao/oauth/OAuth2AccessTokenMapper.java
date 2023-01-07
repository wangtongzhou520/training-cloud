package com.springcloud.study.system.dao.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.study.system.entity.oauth.OAuth2AccessTokenDO;
import org.springframework.stereotype.Repository;

/**
 * oathr2 accessToken服务
 *
 * @author wangtongzhou
 * @since 2020-09-18 13:47
 */
@Repository
public interface OAuth2AccessTokenMapper extends BaseMapper<OAuth2AccessTokenDO> {

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
