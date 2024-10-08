package org.training.cloud.tool.dto.generator.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 表单
 *
 * @author wangtongzhou
 * @since 2024-03-03 17:46
 */
@Data
public class ModifyGeneratorTableDTO implements Serializable {


    @Schema(description = "编号", example = "1")
    @NotNull(message = "表单编号不能为空")
    private Long id;

    /**
     * 生成场景
     */
    @Schema(description = "生成场景", example = "1")
    private Integer scene;


    @Schema(description = "表名称", example = "sys_user")
    @NotNull(message = "表名称不能为空")
    private String tableName;

    @Schema(description = "表描述", example = "用户表")
    @NotNull(message = "表描述不能为空")
    private String tableComment;


    @Schema(description = "备注", example = "备注")
    private String remark;


    @Schema(description = "模块名",  example = "system")
    @NotNull(message = "模块名不能为空")
    private String moduleName;

    @Schema(description = "业务名", example = "user")
    @NotNull(message = "业务名不能为空")
    private String businessName;

    @Schema(description = "类名称", example = "User")
    @NotNull(message = "类名称不能为空")
    private String className;

    @Schema(description = "类描述",  example = "用户表")
    @NotNull(message = "类描述不能为空")
    private String classComment;

    @Schema(description = "作者",  example = "wtz")
    @NotNull(message = "作者不能为空")
    private String author;

    @Schema(description = "父菜单编号", example = "1")
    private Long parentMenuId;

    @Schema(description = "主表的编号", example = "2")
    private Long masterTableId;

    @Schema(description = "树表的父字段编号", example = "3")
    private Long treeParentColumnId;

    @Schema(description = "树表的名字字段编号", example = "4")
    private Long treeNameColumnId;
}
