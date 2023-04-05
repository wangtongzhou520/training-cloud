package org.training.cloud.system.dto.oauth2;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.web.core.vo.PageParam;

/**
 * 分页查询授权客户端
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-02 15:54
 */
@Data
@Accessors(chain = true)
@ApiOperation(value = "分页查询授权客户端")
public class PageOauth2ClientDTO extends PageParam {

    @ApiModelProperty(value = "客户端名称", required = true, example = "客户端名称")
    private String clientName;

}
