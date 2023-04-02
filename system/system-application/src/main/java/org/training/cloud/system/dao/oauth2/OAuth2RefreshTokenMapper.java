package org.training.cloud.system.dao.oauth2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.training.cloud.system.entity.oauth2.Oauth2RefreshToken;
import org.springframework.stereotype.Repository;

/**
 * 刷新令牌
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
@Repository
public interface OAuth2RefreshTokenMapper extends BaseMapper<Oauth2RefreshToken> {

    /**
     * 删除用户信息
     *
     * @param userId userId
     * @return 行数
     */
    int deleteByUserId(Long userId);
}
