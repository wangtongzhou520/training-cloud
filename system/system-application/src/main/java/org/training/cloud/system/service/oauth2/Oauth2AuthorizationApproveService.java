package org.training.cloud.system.service.oauth2;

import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationApprove;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Oauth2授权审批
 *
 * @author wangtongzhou 
 * @since 2023-05-04 21:05
 */
public interface Oauth2AuthorizationApproveService {

    /**
     * 修改审批表信息
     *
     * @param userId
     * @param userType
     * @param clientId
     * @param scopes
     * @return
     */
    boolean modifyAuthorizationApprove(Long userId, Integer userType, String clientId, Map<String, Boolean> scopes);


    /**
     * 查询审批通过列表 未过期不展示
     *
     * @param userId
     * @param userType
     * @param clientId
     * @return
     */
    List<SysOauth2AuthorizationApprove> queryAuthorizationApproveList(Long userId, Integer userType, String clientId);


}
