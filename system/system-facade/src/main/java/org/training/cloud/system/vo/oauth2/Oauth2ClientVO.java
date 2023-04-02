package org.training.cloud.system.vo.oauth2;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 展示信息
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-02 17:15
 */
@Data
public class Oauth2ClientVO implements Serializable {
    @ApiModelProperty(value = "id", required = true, example = "唯一键")
    private Long id;

    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id", required = true, example = "字符串最好能是应用标识类型")
    private String clientId;

    /**
     * 客户端密钥
     */
    @ApiModelProperty(value = "客户端密钥", required = true, example = "字符串")
    private String clientSecret;

    /**
     * 客户端名称
     */
    @ApiModelProperty(value = "客户端名称", required = true, example = "客户端名称")
    private String clientName;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址")
    private String clientLogo;

    /**
     * 应用描述
     */
    @ApiModelProperty(value = "应用描述")
    private String clientDescription;


    /**
     * 授权码类型
     */
    @ApiModelProperty(value = "授权码类型")
    private List<String> grantTypes;



    /**
     * 授权范围
     */
    @ApiModelProperty(value = "授权范围", example = "user_info")
    private List<String> scopes;

    /**
     * 授权令牌有效期
     */
    @ApiModelProperty(value = "授权令牌有效期", required = true, example = "12344")
    private Long accessTokenValiditySeconds;

    /**
     * 刷新令牌有效期
     */
    @ApiModelProperty(value = "刷新令牌有效期", required = true, example = "12344")
    private Long refreshTokenValiditySeconds;


    @ApiModelProperty(value = "创建时间", required = true, example = "12344")
    private Long createTime;
}
