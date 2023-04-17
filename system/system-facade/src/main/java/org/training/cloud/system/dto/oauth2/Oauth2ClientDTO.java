package org.training.cloud.system.dto.oauth2;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

/**
 * 分页查询授权客户端
 *
 * @author wangtongzhou 
 * @since 2023-04-02 15:54
 */
@Data
@Accessors(chain = true)
@Schema(description = "分页查询授权客户端")
public class Oauth2ClientDTO extends PageParam {

    @Schema(description = "客户端名称", required = true, example = "客户端名称")
    private String clientName;

}
