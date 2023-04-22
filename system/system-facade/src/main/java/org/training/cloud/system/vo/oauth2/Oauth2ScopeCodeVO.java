package org.training.cloud.system.vo.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 授权资源管理
 *
 * @author wangtongzhou 
 * @since 2023-04-16 15:52
 */
@Data
public class Oauth2ScopeCodeVO implements Serializable {

    /**
     * 授权范围
     */
    @Schema(description = "授权范围",  example = "read")
    private String scopeName;

    /**
     * 授权范围描述
     */
    @Schema(description = "授权范围描述",  example = "read")
    private String scopeDescription;
}
