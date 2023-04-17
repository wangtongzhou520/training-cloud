package org.training.cloud.system.dto.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

/**
 * Token管理
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-16 14:36
 */
@Data
@Accessors(chain = true)
@Schema(description = "分页查询Token")
public class Oauth2AccessTokenDTO extends PageParam {

    @Schema(description = "用户编号", required = true, example = "666")
    private Long userId;

    @Schema(description = "用户类型,参见 UserTypeEnum 枚举", required = true, example = "2")
    private Integer userType;

    @Schema(description = "客户端编号", required = true, example = "2")
    private String clientId;

}
