package org.training.cloud.tool.vo.file;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Accessors(chain = true)
@Schema(description = "文件分类返回信息")
public class FileCategoryVO implements Serializable {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "父分类ID")
    private Long parentId;

    @Schema(description = "排序序号")
    private Integer seq;
    /**
     * 分类级别
     */
    private String level;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "删除状态（0：未删除，1：已删除）")
    private Boolean deleteState;



}
