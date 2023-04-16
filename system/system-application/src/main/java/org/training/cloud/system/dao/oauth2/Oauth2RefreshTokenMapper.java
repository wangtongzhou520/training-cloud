package org.training.cloud.system.dao.oauth2;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.oauth2.SysOauth2RefreshToken;

/**
 * 刷新令牌
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
@Mapper
public interface Oauth2RefreshTokenMapper extends BaseMapperExtend<SysOauth2RefreshToken> {

    /**
     * 根据刷新令牌查询
     *
     * @param refreshToken
     * @return
     */
    default SysOauth2RefreshToken queryRefreshByRefreshToken(String refreshToken){
        return selectOne(SysOauth2RefreshToken::getRefreshToken,refreshToken);
    }

}
