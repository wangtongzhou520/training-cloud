package org.training.cloud.system.dto.admin.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

/**
 * 管理员查询
 *
 * @author wangtongzhou
 * @since 2023-05-21 21:13
 */
@Data
@Accessors(chain = true)
@Schema(description = "管理员查询")
public class AdminUserDTO extends PageParam {

    @Schema(description = "姓名", required = true, example = "AAA")
    private String userName;

    @Schema(description = "部门ID", required = true, example = "AAA")
    private Long deptId;

}
