package org.training.cloud.system.service.oauth2;

import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.system.dao.oauth2.Oauth2AuthorizationApproveMapper;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationApprove;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

/**
 * Oauth2授权审批
 *
 * @author wangtongzhou
 * @since 2023-05-04 21:08
 */
@Service
public class Oauth2AuthorizationApproveServiceImpl implements Oauth2AuthorizationApproveService {

    /**
     * 默认30
     */
    private static final Integer TIMEOUT = 30 * 24 * 60 * 60;

    @Resource
    private Oauth2AuthorizationApproveMapper authorizationApproveMapper;


    @Override
    public boolean modifyAuthorizationApprove(Long userId, Integer userType, String clientId, Map<String, Boolean> scopes) {
        //检查授权类型
        if (MapUtils.isEmpty(scopes)) {
            return true;
        }
        //进行授权信息修改
        boolean result = false;
        LocalDateTime expireTime = LocalDateTime.now().plusSeconds(TIMEOUT);
        for (Map.Entry<String, Boolean> scope : scopes.entrySet()) {
            result = true;
            addModifyAuthorizationApprove(userId, userType, clientId, scope.getKey(), scope.getValue(), expireTime);
        }
        return result;
    }


    /**
     * 新增修改授权审批表
     *
     * @param userId
     * @param userType
     * @param clientId
     * @param scope
     * @param approved
     * @param expireTime
     */
    void addModifyAuthorizationApprove(Long userId, Integer userType,
                                       String clientId, String scope, Boolean approved, LocalDateTime expireTime) {
        //存在就更新
        SysOauth2AuthorizationApprove authorizationApprove = new SysOauth2AuthorizationApprove()
                .setUserId(userId)
                .setUserType(userType)
                .setClientId(clientId)
                .setScope(scope)
                .setApproveState(approved)
                .setExpiresTime(expireTime);
        if (authorizationApproveMapper.update(authorizationApprove) == 1) {
            return;
        }
        //不存在就删除
        authorizationApproveMapper.insert(authorizationApprove);
    }
}
