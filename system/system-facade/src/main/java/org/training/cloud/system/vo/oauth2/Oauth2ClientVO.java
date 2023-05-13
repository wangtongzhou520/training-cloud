package org.training.cloud.system.vo.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * oauth2 client 展示信息
 *
 * @author wangtongzhou 
 * @since 2023-04-02 17:15
 */
@Data
public class Oauth2ClientVO implements Serializable {
    @Schema(description = "id", required = true, example = "唯一键")
    private Long id;

    /**
     * 客户端id
     */
    @Schema(description = "客户端id", required = true, example = "字符串最好能是应用标识类型")
    private String clientId;

    /**
     * 客户端密钥
     */
    @Schema(description = "客户端密钥", required = true, example = "字符串")
    private String clientSecret;

    /**
     * 客户端名称
     */
    @Schema(description = "客户端名称", required = true, example = "客户端名称")
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
    private List<String> grantTypes;



    /**
     * 授权范围
     */
    @Schema(description = "授权范围", example = "user_info")
    private List<String> scopes;

    /**
     * 授权令牌有效期
     */
    @Schema(description = "授权令牌有效期", required = true, example = "12344")
    private Long accessTokenValiditySeconds;

    /**
     * 刷新令牌有效期
     */
    @Schema(description = "刷新令牌有效期", required = true, example = "12344")
    private Long refreshTokenValiditySeconds;


    @Schema(description = "创建时间", required = true, example = "12344")
    private Long createTime;
}
