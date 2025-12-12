package org.training.cloud.course.vo.material;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@Schema(description = "课程资料返回信息")
public class CourseMaterialVO implements Serializable {

    @Schema(description = "资料ID")
    private Long id;

    @Schema(description = "课程ID")
    private Long courseId;

    @Schema(description = "课时ID（为空表示课程资料）")
    private Long lessonId;

    @Schema(description = "资料名称")
    private String materialName;

    @Schema(description = "资料类型（PPT, PDF, WORD, EXCEL, CODE, OTHER）")
    private String materialType;

    @Schema(description = "资料URL")
    private String materialUrl;

    @Schema(description = "文件大小（字节）")
    private Long fileSize;

    @Schema(description = "下载次数")
    private Integer downloadCount;

    @Schema(description = "是否免费下载（0-否，1-是）")
    private Boolean isFree;

    @Schema(description = "排序序号")
    private Integer sort;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;

}
