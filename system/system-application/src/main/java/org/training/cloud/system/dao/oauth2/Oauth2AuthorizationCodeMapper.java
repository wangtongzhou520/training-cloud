package org.training.cloud.system.dao.oauth2;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationCode;

/**
 * 授权码表
 *
 * @author wangtongzhou 
 * @since 2023-05-05 21:40
 */
@Mapper
public interface Oauth2AuthorizationCodeMapper extends BaseMapperExtend<SysOauth2AuthorizationCode> {

    /**
     * 查询授权码信息
     *
     * @param authorizationCode
     * @return
     */
    default SysOauth2AuthorizationCode queryByCode(String authorizationCode){
        return selectOne(SysOauth2AuthorizationCode::getAuthorizationCode,authorizationCode);
    }
}
