package org.training.cloud.system.controller.oauth2;

import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.utils.josn.JsonUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.security.core.model.AuthUser;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;
import org.training.cloud.system.enums.oauth2.OAuth2GrantTypeEnum;
import org.training.cloud.system.service.oauth2.Oauth2AuthorizationApproveService;
import org.training.cloud.system.service.oauth2.Oauth2ClientService;
import org.training.cloud.system.service.oauth2.Oauth2ScopeCodeService;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.training.cloud.common.core.constant.UserExceptionCode.BAD_REQUEST;
import static org.training.cloud.common.security.core.utils.SecurityUtils.getAuthUser;
import static org.training.cloud.common.security.core.utils.SecurityUtils.getUserId;

/**
 * Oauth2授权申请
 *
 * @author wangtongzhou
 * @since 2023-05-01 20:52
 */
@Tag(name = "Oauth2授权申请")
@RestController
@RequestMapping("/sys/oauth2/")
public class Oauth2OpenController {


    @Resource
    private Oauth2ClientService oauth2ScopeCodeService;

    @Resource
    private Oauth2AuthorizationApproveService oauth2AuthorizationApproveService;


    /**
     * 授权申请页面
     *
     * @param grantType
     * @param clientId
     * @param scope
     * @param redirectUri
     * @param approveState
     * @return
     */
    @PostMapping("/applyAuthorize")
    @Operation(summary = "申请授权", description = "适合授权码模式简化模式")
    @Parameters({
            @Parameter(name = "grant_type", required = true, description = "授权类型", example = "code"),
            @Parameter(name = "client_id", required = true, description = "客户端编号", example = "admin"),
            @Parameter(name = "scope", description = "授权范围", example = "read"),
            @Parameter(name = "redirect_uri", required = true, description =
                    "重定向URI", example = "https://www.baidu.com"),
            @Parameter(name = "approve_state", required = true, description = "授权是否通过", example = "true"),
    })
    public CommonResponse<String> applyAuthorize(@RequestParam("grant_type") String grantType,
                                                 @RequestParam("client_id") String clientId,
                                                 @RequestParam(value = "scope", required = false) String scope,
                                                 @RequestParam("redirect_uri") String redirectUri,
                                                 @RequestParam(value = "approve_state") Boolean approveState) {
        //1. 转化参数
        Map<String, Boolean> scopes = JsonUtils.parseObject(scope, Map.class);
        if (MapUtils.isEmpty(scopes)) {
            scopes = Maps.newHashMap();
        }
        //2. 参数检查
        OAuth2GrantTypeEnum auth2GrantTypeEnum = checkGrantTypeEnum(grantType);
        SysOauth2Client sysOauth2Client = oauth2ScopeCodeService.checkOauth2Client(clientId,
                null, grantType, scopes.keySet(), redirectUri);
        //3. 授权审批表记录
        AuthUser authUser = getAuthUser();
        assert authUser != null;
        if (!oauth2AuthorizationApproveService.modifyAuthorizationApprove(authUser.getId(),
                authUser.getUserType(),clientId,scopes )) {
            return CommonResponse.ok();
        }
        //4. 按照不同的模式进行处理 处理授权码模式和简化模式
        if (auth2GrantTypeEnum==OAuth2GrantTypeEnum.AUTHORIZATION_CODE){

        }
        //简化模式
        return null;
    }

    private OAuth2GrantTypeEnum checkGrantTypeEnum(String grantType) {
        if (StringUtils.equals(grantType,
                OAuth2GrantTypeEnum.AUTHORIZATION_CODE.getDesc())) {
            return OAuth2GrantTypeEnum.AUTHORIZATION_CODE;
        }
        if (StringUtils.equals(grantType,
                OAuth2GrantTypeEnum.IMPLICIT.getDesc())) {
            return OAuth2GrantTypeEnum.IMPLICIT;
        }
        throw new BusinessException(BAD_REQUEST.getCode(), "授权模式只支持授权码以及简化模式");
    }

}
