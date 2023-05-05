package org.training.cloud.system.dao.oauth2;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.dto.oauth2.Oauth2ScopeCodeDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2ScopeCode;

/**
 * 授权资源管理
 *
 * @author wangtongzhou 
 * @since 2023-04-16 15:33
 */
@Mapper
public interface Oauth2ScopeCodeMapper extends BaseMapperExtend<SysOauth2ScopeCode> {

    /**
     * 分页查询
     *
     * @param oauth2ScopeCodeDTO
     * @return
     */
    default PageResponse<SysOauth2ScopeCode> pageScopeCode(Oauth2ScopeCodeDTO oauth2ScopeCodeDTO) {
        return selectPage(oauth2ScopeCodeDTO, new LambdaQueryWrapperExtend<SysOauth2ScopeCode>()
                .eqIfPresent(SysOauth2ScopeCode::getScopeName, oauth2ScopeCodeDTO.getScopeName())
        );
    }
}
