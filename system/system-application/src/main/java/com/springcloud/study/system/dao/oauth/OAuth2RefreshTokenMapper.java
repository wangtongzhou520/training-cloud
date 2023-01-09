package com.springcloud.study.system.dao.oauth;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.springcloud.study.system.entity.oauth.OAuth2RefreshToken;
import org.springframework.stereotype.Repository;

/**
 * 刷新令牌
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
@Repository
public interface OAuth2RefreshTokenMapper extends BaseMapper<OAuth2RefreshToken> {

    /**
     * 删除用户信息
     *
     * @param userId userId
     * @return 行数
     */
    int deleteByUserId(Long userId);
}
