package org.training.cloud.system.dao.oauth2;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;

/**
 * OAuth2 AccessToken服务
 *
 * @author wangtongzhou
 * @since 2020-09-18 13:47
 */
@Mapper
public interface Oauth2AccessTokenMapper extends BaseMapperExtend<SysOauth2AccessToken> {

    /**
     * 根据accessToken查询授权信息
     *
     * @param accessToken
     * @return
     */
    default SysOauth2AccessToken queryAccessModelByAccessToken(String accessToken) {
        return selectOne(SysOauth2AccessToken::getAccessToken, accessToken);
    }

}
