package org.training.cloud.system.dto.permission;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 菜单搜索
 *
 * @author wangtongzhou
 * @since 2023-06-01 21:44
 */
@Data
@Accessors(chain = true)
@Schema(description = "查询菜单信息")
public class MenuDTO implements Serializable {

    @Schema(description = "菜单名称", required = true, example = "芋道")
    private String name;
}
