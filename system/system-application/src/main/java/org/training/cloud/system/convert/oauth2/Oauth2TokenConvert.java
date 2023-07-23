package org.training.cloud.system.convert.oauth2;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.entity.oauth2.SysOauth2AccessToken;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

/**
 * oauth convert
 *
 * @author wangtongzhou
 * @since 2020-09-18 16:39
 */
@Mapper
public interface Oauth2TokenConvert {

    Oauth2TokenConvert INSTANCE = Mappers.getMapper(Oauth2TokenConvert.class);


    /**
     * oAuth2AccessTokenDO convert oAuth2AccessTokenBO
     *
     * @param oAuth2AccessTokenSys oAuth2AccessTokenDO
     * @return oAuth2AccessTokenDO
     */
    Oauth2AccessTokenVO convert(SysOauth2AccessToken oAuth2AccessTokenSys);


    /**
     * pageResponse convert pageAccessToken
     * @param pageResponse
     * @return
     */
    PageResponse<Oauth2AccessTokenVO> convert(PageResponse<SysOauth2AccessToken> pageResponse);

}
