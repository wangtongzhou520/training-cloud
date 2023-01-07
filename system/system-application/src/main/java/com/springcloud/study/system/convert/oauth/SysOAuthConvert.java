package com.springcloud.study.system.convert.oauth;

import com.springcloud.study.system.bo.oauth.OAuth2AccessTokenBO;
import com.springcloud.study.system.entity.oauth.OAuth2AccessTokenDO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * oauth convert
 *
 * @author wangtongzhou
 * @since 2020-09-18 16:39
 */
@Mapper
public interface SysOAuthConvert {

    SysOAuthConvert INSTANCE = Mappers.getMapper(SysOAuthConvert.class);


    /**
     * oAuth2AccessTokenDO convert oAuth2AccessTokenBO
     *
     * @param oAuth2AccessTokenDO oAuth2AccessTokenDO
     * @return oAuth2AccessTokenDO
     */
    @Mappings({})
    OAuth2AccessTokenBO convert(OAuth2AccessTokenDO oAuth2AccessTokenDO);

}
