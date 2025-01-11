package org.training.cloud.tool.dto.file;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;


@Data
@Accessors(chain = true)
@Schema(description = "文件分类分页查询")
public class FileCategoryDTO extends PageParam {

//    @Schema(description = "文件分类ID" )
//    private Long id;

    @Schema(description = "分类名称" )
    private String name;

//    @Schema(description = "父分类ID" )
//    private Long parentId;
//
//    @Schema(description = "分类级别" )
//    private String level;
//
//    @Schema(description = "排序序号" )
//    private Integer seq;
//
//    @Schema(description = "备注" )
//    private String remark;
//
//    @Schema(description = "创建人" )
//    private String createOperator;
//
//    @Schema(description = "修改人" )
//    private String modifiedOperator;
//
//    @Schema(description = "创建时间" )
//    private LocalDateTime gmtCreate;
//
//    @Schema(description = "修改时间" )
//    private LocalDateTime gmtModified;
//
//    @Schema(description = "删除状态（0：未删除，1：已删除）" )
//    private Boolean deleteState;



}


