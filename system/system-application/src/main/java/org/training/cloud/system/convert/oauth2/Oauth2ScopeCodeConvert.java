package org.training.cloud.system.convert.oauth2;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.entity.oauth2.SysOauth2ScopeCode;
import org.training.cloud.system.vo.oauth2.Oauth2ScopeCodeVO;

/**
 * 授权资源管理
 *
 * @author wangtongzhou 
 * @since 2023-04-16 16:01
 */
@Mapper
public interface Oauth2ScopeCodeConvert {
    Oauth2ScopeCodeConvert INSTANCE = Mappers.getMapper(Oauth2ScopeCodeConvert.class);

    /**
     * sysScopeCode convert oauth2ScopeCodeVO
     *
     * @param sysScopeCode
     * @return
     */
    Oauth2ScopeCodeVO convert(SysOauth2ScopeCode sysScopeCode);

    /**
     * 分页转化
     *
     * @param pageResponse
     * @return
     */
    PageResponse<Oauth2ScopeCodeVO> convert(PageResponse<SysOauth2ScopeCode> pageResponse);

}
