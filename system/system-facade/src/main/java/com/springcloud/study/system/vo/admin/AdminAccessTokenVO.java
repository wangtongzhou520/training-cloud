package com.springcloud.study.system.vo.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 授权token
 *
 * @author wangtongzhou
 * @since 2020-11-11 20:53
 */
@Data
@Accessors(chain = true)
public class AdminAccessTokenVO implements Serializable {

    @ApiModelProperty(value = "访问令牌", required = true)
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌", required = true)
    private String refreshToken;

    @ApiModelProperty(value = "过期时间", required = true)
    private Date expiresTime;
}
