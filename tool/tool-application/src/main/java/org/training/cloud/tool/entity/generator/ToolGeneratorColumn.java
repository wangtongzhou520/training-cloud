package org.training.cloud.tool.entity.generator;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * 代码生成列
 *
 * @author wangtongzhou
 * @since 2024-02-27 20:35
 */
@TableName(value = "tool_generator_column")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ToolGeneratorColumn extends BaseDO {
    /**
     * ID 编号
     */
    @TableId
    private Long id;

    /**
     * 表编号
     */
    private Long tableId;

    /**
     * 字段名
     */
    private String columnName;
    /**
     * 数据库字段类型
     */
    private String dataType;
    /**
     * 字段描述
     */
    private String columnComment;
    /**
     * 是否允许为空
     */
    private Boolean nullable;
    /**
     * 是否主键
     */
    private Boolean primaryKey;
    /**
     * 是否自增
     */
    private Boolean autoIncrement;
    /**
     * 字段排序
     */
    private Integer columnSort;


    /**
     * Java 属性类型
     */
    private String javaType;
    /**
     * Java 属性名
     */
    private String javaField;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 数据示例
     */
    private String example;


    /**
     * 是否为 Create 创建操作的字段
     */
    private Boolean createOperation;
    /**
     * 是否为 Update 更新操作的字段
     */
    private Boolean updateOperation;
    /**
     * 是否为 List 查询操作的字段
     */
    private Boolean listOperation;

    /**
     * List 查询操作的条件类型
     */
    private String listOperationCondition;
    /**
     * 是否为 List 查询操作的返回字段
     */
    private Boolean listOperationResult;

    // ========== UI 相关字段 ==========

    /**
     * 显示类型
     */
    private String htmlType;

}
