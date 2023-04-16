package org.training.cloud.system.service.oauth2;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.oauth2.Oauth2ScopeCodeDTO;
import org.training.cloud.system.vo.oauth2.Oauth2ScopeCodeVO;

/**
 * 授权资源的管理
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-16 15:46
 */
public interface Oauth2ScopeCodeService {

    /**
     * 分页查询授权资源
     *
     * @param oauth2ScopeCodeDTO
     * @return
     */
    PageResponse<Oauth2ScopeCodeVO> pageOauth2ScopeCode(Oauth2ScopeCodeDTO oauth2ScopeCodeDTO);

}
