package org.training.cloud.system.vo.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.KeyValue;

import java.io.Serializable;
import java.util.List;

/**
 * 授权信息
 *
 * @author wangtongzhou
 * @since 2023-05-10 21:42
 */
@Data
@Accessors(chain = true)
public class Oauth2AuthorizationInfoVO implements Serializable {

    @Schema(description = "客户端Id")
    private String clientId;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "客户端头像地址")
    private String clientLogo;

    @Schema(description = "授权码客户端信息")
    private List<KeyValue<String, Boolean>> scopes;

}
