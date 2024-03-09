package org.training.cloud.tool.vo.generator.column;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 字段定义
 *
 * @author wangtongzhou 18635604249
 * @since 2024-03-09 17:06
 */
@Data
public class GeneratorColumnVO implements Serializable {
    @Schema(description = "编号", example = "1")
    @NotNull(message = "列表编号不能为空")
    private Long id;

    @Schema(description = "编号", example = "1")
    @NotNull(message = "表单编号不能为空")
    private Long tableId;

    @Schema(description = "字段名",  example = "user_name")
    @NotNull(message = "字段名不能为空")
    private String columnName;

    @Schema(description = "字段类型",  example = "int(11)")
    @NotNull(message = "字段类型不能为空")
    private String dataType;

    @Schema(description = "字段描述", example = "用户名")
    @NotNull(message = "字段描述不能为空")
    private String columnComment;


    @Schema(description = "是否允许为空",  example = "true")
    @NotNull(message = "是否允许为空不能为空")
    private Boolean nullable;

    @Schema(description = "是否主键",  example = "false")
    @NotNull(message = "是否主键不能为空")
    private Boolean primaryKey;

    @Schema(description = "是否自增", example = "true")
    @NotNull(message = "是否自增不能为空")
    private Boolean autoIncrement;


    @Schema(description = "排序",  example = "10")
    @NotNull(message = "排序不能为空")
    private Integer ordinalPosition;


    @Schema(description = "Java属性类型", example = "Integer")
    @NotNull(message = "Java属性类型不能为空")
    private String javaType;


    @Schema(description = "Java 属性名",  example = "Integer")
    @NotNull(message = "Java属性名不能为空")
    private String javaField;

    @Schema(description = "字典类型", example = "sys_gender")
    private String dictType;

    @Schema(description = "数据示例", example = "1")
    private String example;


    @Schema(description = "是否新增字段",  example = "true")
    @NotNull(message = "是否新增字段不允许为空")
    private Boolean createOperation;

    @Schema(description = "是否修改字段",  example = "true")
    @NotNull(message = "是否修改字段不允许为空")
    private Boolean updateOperation;

    @Schema(description = "是否查询字段",  example = "true")
    @NotNull(message = "是否查询字段不允许为空")
    private Boolean listOperation;

    @Schema(description = "查询方式",  example = "true")
    @NotNull(message = "查询方式字段不允许为空")
    private String listOperationCondition;


    @Schema(description = "是否查询返回字段",  example = "true")
    @NotNull(message = "查询返回字段不能为空")
    private Boolean listOperationResult;


    @Schema(description = "显示类型",  example = "input")
    @NotNull(message = "显示类型不能为空")
    private String htmlType;
}
