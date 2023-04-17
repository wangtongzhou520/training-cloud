package org.training.cloud.system.dao.oauth2;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.dto.oauth2.Oauth2AccessTokenDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;

import java.util.Date;
import java.util.List;

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
    default SysOauth2AccessToken queryAccessByAccessToken(String accessToken) {
        return selectOne(SysOauth2AccessToken::getAccessToken, accessToken);
    }

    /**
     * 根据刷新令牌查询所有授权令牌
     *
     * @param refreshToken
     * @return
     */
    default List<SysOauth2AccessToken> queryAccessListByRefreshToken(String refreshToken){
        return selectList(SysOauth2AccessToken::getAccessToken, refreshToken);
    }


    /**
     * 分页查询Token
     *
     * @param oauth2AccessTokenDTO
     * @return
     */
    default PageResponse<SysOauth2AccessToken> pageAccessToken(Oauth2AccessTokenDTO oauth2AccessTokenDTO){
        return selectPage(oauth2AccessTokenDTO, new LambdaQueryWrapperExtend<SysOauth2AccessToken>()
                .eqIfPresent(SysOauth2AccessToken::getUserId, oauth2AccessTokenDTO.getUserId())
                .eqIfPresent(SysOauth2AccessToken::getClientId, oauth2AccessTokenDTO.getClientId())
                .eqIfPresent(SysOauth2AccessToken::getUserType, oauth2AccessTokenDTO.getUserType())
                .gt(SysOauth2AccessToken::getExpiresTime,new Date())
        );
    }

}
