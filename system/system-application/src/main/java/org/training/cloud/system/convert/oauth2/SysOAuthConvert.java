package org.training.cloud.system.convert.oauth2;

import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;
import org.training.cloud.system.vo.oauth2.OAuth2AccessTokenVO;
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
     * @param oAuth2AccessTokenSys oAuth2AccessTokenDO
     * @return oAuth2AccessTokenDO
     */
    @Mappings({})
    OAuth2AccessTokenVO convert(SysOauth2AccessToken oAuth2AccessTokenSys);

}
