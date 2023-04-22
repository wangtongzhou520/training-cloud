package org.training.cloud.system.dto.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

/**
 * 授权资源
 *
 * @author wangtongzhou 
 * @since 2023-04-16 15:54
 */
@Data
@Accessors(chain = true)
@Schema(description = "分页查询授权资源")
public class Oauth2ScopeCodeDTO extends PageParam {

    @Schema(description = "授权范围",  example = "read")
    private String scopeName;
}
