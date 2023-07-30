package org.training.cloud.system.vo.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 授权token
 *
 * @author wangtongzhou
 * @since 2020-11-11 20:53
 */
@Data
@Schema(description = "登录")
public class AuthLoginVO implements Serializable {


    @Schema(description = "用户编号", required = true)
    private Long userId;

    @Schema(description = "用户类型", required = true)
    private Integer userType;

    @Schema(description = "访问令牌", required = true)
    private String accessToken;

    @Schema(description = "刷新令牌", required = true)
    private String refreshToken;

    @Schema(description = "过期时间", required = true)
    private LocalDateTime expiresTime;
}
