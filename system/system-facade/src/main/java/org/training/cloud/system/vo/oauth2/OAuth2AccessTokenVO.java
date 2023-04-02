package org.training.cloud.system.vo.oauth2;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * OAuth2AccessTokenBO
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
@Data
@Accessors(chain = true)
public class OAuth2AccessTokenVO {

    /**
     * 访问令牌
     */
    @ApiModelProperty(value = "访问令牌", required = true)
    private String accessToken;
    /**
     * 刷新令牌
     */
    @ApiModelProperty(value = "刷新令牌", required = true)
    private String refreshToken;

    /**
     * 过期时间
     */
    @ApiModelProperty(value = "过期时间", required = true)
    private Date expiresTime;

}
