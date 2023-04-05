package org.training.cloud.system.dto.oauth2;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 新增授权客户端
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-02 14:25
 */
@Data
@Accessors(chain = true)
@Schema(description = "新增授权客户端")
public class AddOauth2ClientDTO implements Serializable {

    /**
     * 客户端id
     */
    @Schema(description = "客户端id", required = true, example = "字符串最好能是应用标识类型")
    @NotEmpty(message = "客户端id不允许为空")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥", required = true, example = "字符串")
    @NotEmpty(message = "客户端密钥不允许为空")
    private String clientSecret;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称", required = true, example = "客户端名称")
    @NotEmpty(message = "客户端名称不允许为空")
    private String clientName;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String clientLogo;

    /**
     * 应用描述
     */
    @Schema(description = "应用描述")
    private String clientDescription;


    /**
     * 授权码类型
     */
    @Schema(description = "授权码类型")
    @NotEmpty(message = "授权码类型不允许为空")
    private List<String> grantTypes;

    /**
     * 重定向地址
     */
    @Schema(description = "重定向地址")
    @NotEmpty(message = "重定向地址不允许为空")
    private String redirectUrl;

    /**
     * 授权范围
     */
    @Schema(description = "授权范围", example = "user_info")
    @NotEmpty(message = "重定向地址不允许为空")
    private List<String> scopes;

    /**
     * 授权令牌有效期
     */
    @Schema(description = "授权令牌有效期", required = true, example = "12344")
    @NotNull(message = "授权令牌有效期不允许为空")
    private Long accessTokenValiditySeconds;

    /**
     * 刷新令牌有效期
     */
    @Schema(description = "刷新令牌有效期", required = true, example = "12344")
    @NotNull(message = "刷新令牌有效期不允许为空")
    private Long refreshTokenValiditySeconds;


}
