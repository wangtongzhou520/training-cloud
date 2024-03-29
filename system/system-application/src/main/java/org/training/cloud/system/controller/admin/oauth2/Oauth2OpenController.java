package org.training.cloud.system.controller.admin.oauth2;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.utils.josn.JsonUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.security.core.annotations.NotAuthentication;
import org.training.cloud.common.security.core.model.AuthUser;
import org.training.cloud.system.convert.oauth2.Oauth2OpenConvert;
import org.training.cloud.system.entity.oauth2.SysOauth2AuthorizationApprove;
import org.training.cloud.system.entity.oauth2.SysOauth2Client;
import org.training.cloud.system.enums.oauth2.OAuth2GrantTypeEnum;
import org.training.cloud.system.service.oauth2.Oauth2AuthorizationApproveService;
import org.training.cloud.system.service.oauth2.Oauth2ClientService;
import org.training.cloud.system.service.oauth2.Oauth2GrantService;
import org.training.cloud.system.service.oauth2.Oauth2TokenService;
import org.training.cloud.system.utils.HttpUtil;
import org.training.cloud.system.utils.Oauth2Util;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;
import org.training.cloud.system.vo.oauth2.Oauth2AuthorizationInfoVO;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static org.training.cloud.common.core.constant.UserExceptionCode.BAD_REQUEST;
import static org.training.cloud.common.security.core.utils.SecurityUtils.getAuthUser;

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

    @Resource
    private Oauth2GrantService oauth2GrantService;

    @Resource
    private Oauth2ClientService oauth2ClientService;

    @Resource
    private Oauth2TokenService oauth2TokenService;


    @GetMapping("/authorize")
    @Operation(summary = "获得授权信息", description = "适合授权码模式简化模式")
    @Parameter(name = "clientId", required = true, description = "客户端编号", example = "admin")
    public CommonResponse<Oauth2AuthorizationInfoVO> authorize(@RequestParam("clientId") String clientId) {
        //验证客户端
        SysOauth2Client sysOauth2Client = oauth2ClientService.checkOauth2Client(clientId, null,
                null, null, null);
        //获取客户端授权信息
        AuthUser authUser = getAuthUser();
        assert authUser != null;
        List<SysOauth2AuthorizationApprove> authorizationApproves = oauth2AuthorizationApproveService
                .queryAuthorizationApproveList(authUser.getId(), authUser.getUserType(), clientId);
        return CommonResponse.ok(Oauth2OpenConvert.INSTANCE.convert(sysOauth2Client,
                authorizationApproves));
    }


    /**
     * 授权申请页面
     *
     * @param grantType
     * @param clientId
     * @param scope
     * @return
     */
    @PostMapping("/applyAuthorize")
    @Operation(summary = "申请授权", description = "适合授权码模式简化模式")
    @Parameters({
            @Parameter(name = "grant_type", required = true, description = "授权类型", example = "code"),
            @Parameter(name = "client_id", required = true, description = "客户端编号", example = "admin"),
            @Parameter(name = "scope", description = "授权范围", example = "read"),
            @Parameter(name = "redirect_url", required = true, description =
                    "重定向URL", example = "https://www.baidu.com"),
    })
    public CommonResponse<String> applyAuthorize(@RequestParam("grant_type") String grantType,
                                                 @RequestParam("client_id") String clientId,
                                                 @RequestParam(value = "scope", required = false) String scope,
                                                 @RequestParam("redirect_url") String redirectUrl,
                                                 @RequestParam(value = "state", required = false) String state) {
        //1. 转化参数
        Map<String, Boolean> scopes = JsonUtils.parseObject(scope, Map.class);
        if (MapUtils.isEmpty(scopes)) {
            scopes = Maps.newHashMap();
        }
        //2. 参数检查
        OAuth2GrantTypeEnum auth2GrantTypeEnum = checkGrantTypeEnum(grantType);
        SysOauth2Client sysOauth2Client = oauth2ScopeCodeService.checkOauth2Client(clientId,
                null, grantType, scopes.keySet(), redirectUrl);
        //3. 授权审批表记录
        AuthUser authUser = getAuthUser();
        assert authUser != null;
        if (!oauth2AuthorizationApproveService.modifyAuthorizationApprove(authUser.getId(),
                authUser.getUserType(), clientId, scopes)) {
            return CommonResponse.ok();
        }
        //4. 按照不同的模式进行处理 处理授权码模式和简化模式
        List<String> approveScopes = new ArrayList<>(scopes.keySet());
        if (auth2GrantTypeEnum == OAuth2GrantTypeEnum.AUTHORIZATION_CODE) {
            return CommonResponse.ok(getAuthorizationCodeRedirect(authUser.getId(),
                    authUser.getUserType(), sysOauth2Client, approveScopes,
                    redirectUrl, state));
        }
        //简化模式 直接返回accessToken
        return CommonResponse.ok(getImplicitGrantRedirect(authUser.getId(),
                authUser.getUserType(), sysOauth2Client, approveScopes, redirectUrl));
    }


    /**
     * 获取授权令牌
     *
     * @param grantType
     * @param code
     * @param redirectUrl
     * @param username
     * @param password
     * @param scope
     * @param refreshToken
     * @return
     */
    @PostMapping("/token")
    @Operation(summary = "获得访问令牌")
    @NotAuthentication
    @Parameters({
            @Parameter(name = "code", description = "授权范围", example = "read"),
            @Parameter(name = "grant_type", required = true, description = "授权类型", example = "code"),
            @Parameter(name = "code", description = "授权范围", example = "read"),
            @Parameter(name = "redirect_url", description = "重定向 URI", example = "https://www.baidu.com"),
            @Parameter(name = "state", description = "状态", example = "1"),
            @Parameter(name = "username", example = "admin"),
            @Parameter(name = "password", example = "123456"),
            @Parameter(name = "scope", example = "user_info"),
            @Parameter(name = "refresh_token", example = "123424233"),
    })
    public CommonResponse<Oauth2AccessTokenVO> postAccessToken(HttpServletRequest request,
                                                               @RequestParam("grant_type") String grantType,
                                                               @RequestParam(value = "code", required = false) String code,
                                                               @RequestParam(value = "state", required = false) String state,
                                                               @RequestParam(value = "redirect_url", required = false) String redirectUrl,
                                                               @RequestParam(value = "username", required = false) String username,
                                                               @RequestParam(value = "password", required = false) String password,
                                                               @RequestParam(value = "scope", required = false) String scope,
                                                               @RequestParam(value = "refresh_token", required = false) String refreshToken) {
        //1. 参数校验
        List<String> scopes = Lists.newArrayList();
        if (StringUtils.isNotBlank(scope)) {
            scopes = Arrays.asList(scope.split(","));
        }
        //授权类型检查
        OAuth2GrantTypeEnum auth2GrantTypeEnum = OAuth2GrantTypeEnum.getByDesc(grantType);
        if (Objects.isNull(auth2GrantTypeEnum)) {
            throw new BusinessException(BAD_REQUEST.getCode(), grantType + "不支持该授权类型");
        }
        if (auth2GrantTypeEnum == OAuth2GrantTypeEnum.IMPLICIT) {
            throw new BusinessException(BAD_REQUEST.getCode(), "implicit" +
                    "模式不支持Token访问");
        }
        //客户端检查
        String[] clientIdAndSecret = getClientInfo(request);
        SysOauth2Client sysOauth2Client = oauth2ClientService.checkOauth2Client(clientIdAndSecret[0],
                clientIdAndSecret[1], grantType, scopes, redirectUrl);
        //2. 根据不同的模式进行处理
        Oauth2AccessTokenVO result;
        switch (auth2GrantTypeEnum) {
            case AUTHORIZATION_CODE:
                result = oauth2GrantService.grantAuthorizationCodeAccessToken(sysOauth2Client.getClientId(), code, redirectUrl, state);
                break;
            case PASSWORD:
                result = oauth2GrantService.grantPassWord(username, password, sysOauth2Client.getClientId(), scopes);
                break;
            case REFRESH_TOKEN:
                result = oauth2GrantService.grantRefreshAccessToken(refreshToken, sysOauth2Client.getClientId());
                break;
            default:
                throw new IllegalArgumentException("暂不支持");
        }
        return CommonResponse.ok(result);
    }

    @PermitAll
    @PostMapping("/checkToken")
    @Operation(summary = "校验访问令牌")
    @Parameter(name = "token", required = true, description = "访问令牌", example = "admin")
    public CommonResponse<Oauth2AccessTokenVO> checkToken(HttpServletRequest request,
                                                          @RequestParam("token") String token) {
        //检查客户端参数
        String[] clientIdAndSecret = getClientInfo(request);
        oauth2ClientService.checkOauth2Client(clientIdAndSecret[0],
                clientIdAndSecret[1], null, null, null);
        //检查token
        Oauth2AccessTokenVO accessTokenDO = oauth2TokenService.checkAccessToken(token);
        return CommonResponse.ok(accessTokenDO);
    }

    @DeleteMapping("/token")
    @PermitAll
    @Operation(summary = "删除token")
    @Parameter(name = "token", required = true, description = "访问令牌", example = "admin")
    public CommonResponse<Boolean> removeToken(HttpServletRequest request,
                                               @RequestParam("token") String token) {
        //检查客户端参数
        String[] clientIdAndSecret = getClientInfo(request);
        oauth2ClientService.checkOauth2Client(clientIdAndSecret[0],
                clientIdAndSecret[1], null, null, null);
        return CommonResponse.ok(oauth2TokenService.removeToken(token));
    }


    private String getImplicitGrantRedirect(Long userId, Integer userType, SysOauth2Client client, List<String> scopes, String redirectUrl) {
        // 1. 创建 access token 访问令牌
        Oauth2AccessTokenVO accessTokenDO = oauth2GrantService.grantImplicit(userId, userType, client.getClientId(), scopes);
        Assert.notNull(accessTokenDO, "访问令牌不能为空");
        // 2. 拼接重定向的 URL
        return Oauth2Util.buildImplicitRedirectUrl(redirectUrl, accessTokenDO.getAccessToken());
    }


    private String getAuthorizationCodeRedirect(Long userId, Integer userType, SysOauth2Client sysOauth2Client,
                                                List<String> scopes,
                                                String redirectUrl,
                                                String state) {
        // 1. 创建 code 授权码
        String authorizationCode = oauth2GrantService.grantAuthorizationCode(userId, userType,
                sysOauth2Client.getClientId(), scopes, redirectUrl, state);
        // 2. 拼接重定向的 URL
        return Oauth2Util.buildAuthorizationCodeRedirectUrl(redirectUrl, authorizationCode, state);
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


    private String[] getClientInfo(HttpServletRequest request) {
        String[] clientIdAndSecret = HttpUtil.getClientInfo(request);
        if (Objects.isNull(clientIdAndSecret) || clientIdAndSecret.length != 2) {
            throw new BusinessException(BAD_REQUEST.getCode(), "客户端信息异常");
        }
        return clientIdAndSecret;
    }
}
