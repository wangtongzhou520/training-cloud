package org.training.cloud.system.dao.oauth2;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.dto.oauth2.PageOauth2ClientDTO;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;

/**
 * 授权客户端
 *
 * @author wangtongzhou 
 * @since 2023-04-02 14:19
 */
@Mapper
public interface Oauth2ClientMapper extends BaseMapperExtend<SysOauth2Client> {

    default PageResponse<SysOauth2Client> selectPage(PageOauth2ClientDTO pageOauth2ClientDTO) {
        return selectPage(pageOauth2ClientDTO, new LambdaQueryWrapperExtend<SysOauth2Client>()
                .likeIfPresent(SysOauth2Client::getClientName, pageOauth2ClientDTO.getClientName())
        );
    }


    default SysOauth2Client selectByClientId(String clientId) {
        return selectOne(SysOauth2Client::getClientId, clientId);
    }
}
