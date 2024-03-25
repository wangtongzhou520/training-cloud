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
    private Long id;

    @Schema(description = "编号", example = "1")
    private Long tableId;

    @Schema(description = "字段名",  example = "user_name")
    private String columnName;

    @Schema(description = "字段类型",  example = "int(11)")
    private String dataType;

    @Schema(description = "字段描述", example = "用户名")
    private String columnComment;


    @Schema(description = "是否允许为空",  example = "true")
    private Boolean nullable;

    @Schema(description = "是否主键",  example = "false")
    private Boolean primaryKey;

    @Schema(description = "是否自增", example = "true")
    private Boolean autoIncrement;


    @Schema(description = "排序",  example = "10")
    private Integer columnSort;


    @Schema(description = "Java属性类型", example = "Integer")
    private String javaType;


    @Schema(description = "Java 属性名",  example = "Integer")
    private String javaField;

    @Schema(description = "字典类型", example = "sys_gender")
    private String dictType;

    @Schema(description = "数据示例", example = "1")
    private String example;


    /**
     * 是否为新增操作字段
     */
    @Schema(description = "是否为新增操作字段", example = "true")
    private Boolean addField;
    /**
     * 是否为更新操作字段
     */
    @Schema(description = "是否为更新操作字段", example = "true")
    private Boolean modifyField;
    /**
     * 是否为查询字段
     */
    @Schema(description = "是否为查询字段", example = "true")
    private Boolean queryField;

    /**
     * 查询条件
     */
    @Schema(description = "查询条件", example = "true")
    private String queryConditionField;
    /**
     * 是否为查询返回字段
     */
    @Schema(description = "是否为查询返回字段", example = "true")
    private Boolean queryResultField;

    @Schema(description = "显示类型",  example = "input")
    private String htmlType;
}
