package org.training.cloud.system.dto.dept;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 部门查询
 *
 * @author wangtongzhou
 * @since 2024-01-05 21:38
 */
@Data
@Accessors(chain = true)
@Schema(description = "查询部门信息")
public class DeptDTO implements Serializable {

    /**
     * 部门名称
     */
    @Schema(description = "部门名称")
    private String name;


    @Schema(description = "是否启用")
    private Boolean deleteState;

}
