package org.training.cloud.course.dto.material;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(description = "课程资料查询")
public class CourseMaterialDTO extends PageParam implements Serializable {

    @Schema(description = "课程ID")
    private Long courseId;

    @Schema(description = "课时ID")
    private Long lessonId;

    @Schema(description = "资料名称（模糊查询）")
    private String materialName;

    @Schema(description = "资料类型")
    private String materialType;

    @Schema(description = "是否免费下载（0-否，1-是）")
    private Boolean isFree;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;

}
