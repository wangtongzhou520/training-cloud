package org.training.cloud.tool.vo.generator.table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 表
 *
 * @author wangtongzhou
 * @since 2024-03-09 16:26
 */
@Data
public class GeneratorTableVO implements Serializable {

    @Schema(description = "编号", example = "1")
    private Long id;

    @Schema(description = "数据源ID", example = "1")
    private Long dataSourceConfigId;

    @Schema(description = "数据源名称", example = "系统管理")
    private String dataSourceConfigName;

    /**
     * 生成场景
     */
    @Schema(description = "生成场景", example = "1")
    private Integer scene;


    @Schema(description = "表名称", example = "sys_user")
    private String tableName;

    @Schema(description = "表描述", example = "用户表")
    private String tableComment;


    @Schema(description = "备注", example = "备注")
    private String remark;


    @Schema(description = "模块名",  example = "system")
    private String moduleName;

    @Schema(description = "业务名", example = "user")
    private String businessName;

    @Schema(description = "类名称", example = "User")
    private String className;

    @Schema(description = "类描述",  example = "用户表")
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
