package org.training.cloud.course.dto.material;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(description = "课程资料新增")
public class AddCourseMaterialDTO implements Serializable {

    @Schema(description = "课程ID")
    @NotNull(message = "课程ID不能为空")
    private Long courseId;

    @Schema(description = "课时ID（为空表示课程资料）")
    private Long lessonId;

    @Schema(description = "资料名称")
    @NotBlank(message = "资料名称不能为空")
    private String materialName;

    @Schema(description = "资料类型（PPT, PDF, WORD, EXCEL, CODE, OTHER）")
    @NotBlank(message = "资料类型不能为空")
    private String materialType;

    @Schema(description = "资料URL")
    @NotBlank(message = "资料URL不能为空")
    private String materialUrl;

    @Schema(description = "文件大小（字节）")
    @NotNull(message = "文件大小不能为空")
    private Long fileSize;

    @Schema(description = "是否免费下载（0-否，1-是）")
    @NotNull(message = "是否免费下载不能为空")
    private Boolean isFree;

    @Schema(description = "排序序号")
    @NotNull(message = "排序序号不能为空")
    private Integer sort;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    @NotNull(message = "删除状态不能为空")
    private Boolean deleteState;

}
