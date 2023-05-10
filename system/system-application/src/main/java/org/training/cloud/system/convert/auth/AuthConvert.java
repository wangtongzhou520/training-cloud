package org.training.cloud.system.convert.auth;

import org.mapstruct.factory.Mappers;
import org.training.cloud.system.vo.auth.AuthLoginVO;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;

/**
 * 授权
 *
 * @author wangtongzhou
 * @since 2023-05-10 07:11
 */
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    /**
     * oauth2AccessTokenVO convert authLoginVO
     *
     * @param oauth2AccessTokenVO
     * @return
     */
    AuthLoginVO convert(Oauth2AccessTokenVO oauth2AccessTokenVO);


}
