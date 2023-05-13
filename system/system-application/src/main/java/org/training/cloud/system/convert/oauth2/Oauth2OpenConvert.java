package org.training.cloud.system.convert.oauth2;

import com.google.common.collect.Lists;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.KeyValue;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationApprove;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;
import org.training.cloud.system.vo.oauth2.Oauth2AuthorizationInfoVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangtongzhou
 * @since 2023-05-12 07:32
 */
@Mapper
public interface Oauth2OpenConvert {

    Oauth2OpenConvert INSTANCE = Mappers.getMapper(Oauth2OpenConvert.class);


    default Oauth2AuthorizationInfoVO convert(SysOauth2Client sysOauth2Client
            , List<SysOauth2AuthorizationApprove> authorizationApproves) {
        List<KeyValue<String, Boolean>> scopes = Lists.newArrayList();
        Map<String, SysOauth2AuthorizationApprove> authorizationApproveMap = authorizationApproves.stream()
                .collect(Collectors.toMap(SysOauth2AuthorizationApprove::getScope, x -> x));
        sysOauth2Client.getScopes().stream().forEach(x -> {
            SysOauth2AuthorizationApprove approve = authorizationApproveMap.get(x);
            scopes.add(new KeyValue<>(x, approve != null ? approve.getApproveState() : false));
        });
        Oauth2AuthorizationInfoVO authorizationInfoVO =
                new Oauth2AuthorizationInfoVO();
        authorizationInfoVO.setClientId(sysOauth2Client.getClientId())
                .setClientLogo(sysOauth2Client.getClientLogo())
                .setClientName(sysOauth2Client.getClientName())
                .setScopes(scopes);
        return authorizationInfoVO;
    }

}
