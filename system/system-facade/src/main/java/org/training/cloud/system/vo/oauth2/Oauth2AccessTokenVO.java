package org.training.cloud.system.vo.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * OAuth2AccessTokenBO
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
@Data
@Accessors(chain = true)
public class Oauth2AccessTokenVO implements Serializable {

    @Schema(description = "编号", required = true, example = "1024")
    private Long id;
    /**
     * 访问令牌
     */
    @Schema(description = "访问令牌", required = true)
    private String accessToken;
    /**
     * 刷新令牌
     */
    @Schema(description = "刷新令牌", required = true)
    private String refreshToken;

    /**
     * 过期时间
     */
    @Schema(description = "过期时间", required = true)
    private LocalDateTime expiresTime;


    @Schema(description = "用户类型", required = true)
    private Integer userType;


    @Schema(description = "用户Id", required = true)
    private Long userId;

    @Schema(description = "授权范围", required = true)
    private List<String> scopes;

}
