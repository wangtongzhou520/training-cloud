package org.training.cloud.system.service.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.oauth2.Oauth2ScopeCodeConvert;
import org.training.cloud.system.dao.oauth2.Oauth2ScopeCodeMapper;
import org.training.cloud.system.dto.oauth2.Oauth2ScopeCodeDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2ScopeCode;
import org.training.cloud.system.vo.oauth2.Oauth2ScopeCodeVO;

/**
 * 授权资源管理
 *
 * @author wangtongzhou 
 * @since 2023-04-16 15:50
 */
@Service
public class Oauth2ScopeCodeServiceImpl implements Oauth2ScopeCodeService {
    @Autowired
    private Oauth2ScopeCodeMapper oauth2ScopeCodeMapper;

    @Override
    public PageResponse<Oauth2ScopeCodeVO> pageOauth2ScopeCode(Oauth2ScopeCodeDTO oauth2ScopeCodeDTO) {
        PageResponse<SysOauth2ScopeCode> pageResponse =
                oauth2ScopeCodeMapper.pageScopeCode(oauth2ScopeCodeDTO);
        return Oauth2ScopeCodeConvert.INSTANCE.convert(pageResponse);
    }
}
