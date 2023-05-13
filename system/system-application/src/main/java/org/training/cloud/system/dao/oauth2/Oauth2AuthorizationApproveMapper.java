package org.training.cloud.system.dao.oauth2;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationApprove;

import java.util.List;

/**
 * Oauth2审批表
 *
 * @author wangtongzhou
 * @since 2023-05-04 21:04
 */
@Mapper
public interface Oauth2AuthorizationApproveMapper extends BaseMapperExtend<SysOauth2AuthorizationApprove> {

    /**
     * 修改
     *
     * @param authorizationApprove
     * @return
     */
    default int update(SysOauth2AuthorizationApprove authorizationApprove) {
        return update(authorizationApprove, new LambdaQueryWrapperExtend<SysOauth2AuthorizationApprove>()
                .eq(SysOauth2AuthorizationApprove::getUserId, authorizationApprove.getUserId())
                .eq(SysOauth2AuthorizationApprove::getUserType, authorizationApprove.getUserType())
                .eq(SysOauth2AuthorizationApprove::getClientId, authorizationApprove.getClientId())
                .eq(SysOauth2AuthorizationApprove::getScope, authorizationApprove.getScope())
        );
    }

    /**
     * 根据用户信息和client查询审批
     *
     * @param userId
     * @param userType
     * @param clientId
     * @return
     */
    default List<SysOauth2AuthorizationApprove> selectByUserAndClientId(Long userId, Integer userType, String clientId) {
        return selectList(new LambdaQueryWrapperExtend<SysOauth2AuthorizationApprove>()
                .eq(SysOauth2AuthorizationApprove::getUserId, userId)
                .eq(SysOauth2AuthorizationApprove::getUserType, userType)
                .eq(SysOauth2AuthorizationApprove::getClientId, clientId)
        );
    }

}
