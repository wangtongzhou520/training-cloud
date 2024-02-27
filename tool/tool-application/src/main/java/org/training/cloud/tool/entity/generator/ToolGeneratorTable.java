package org.training.cloud.tool.entity.generator;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * 代码生成表
 *
 * @author wangtongzhou
 * @since 2024-02-27 20:09
 */
@TableName(value = "tool_generator_table")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ToolGeneratorTable extends BaseDO {
    /**
     * ID 编号
     */
    @TableId
    private Long id;

    /**
     * 数据源编号
     */
    private Long dataSourceConfigId;

    /**
     * 生成场景
     */
    private Integer scene;


    /**
     * 表名称
     */
    private String tableName;

    /**
     * 表描述
     */
    private String tableComment;
    /**
     * 备注
     */
    private String remark;


    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 业务名，即二级目录
     */
    private String businessName;

    /**
     * 类名称
     */
    private String className;
    /**
     * 类描述
     */
    private String classComment;
    /**
     * 作者
     */
    private String author;


//    /**
//     * 模板类型
//     */
//    private Integer templateType;
//
//    /**
//     * 代码生成的前端类型
//     */
//    private Integer frontType;

    // ========== 菜单相关字段 ==========

    /**
     * 父菜单编号
     */
    private Long parentMenuId;

    /**
     * 主表的编号
     */
    private Long masterTableId;
//    /**
//     * 【自己】子表关联主表的字段编号
//     *
//     * 关联 {@link CodegenColumnDO#getId()}
//     */
//    private Long subJoinColumnId;
//    /**
//     * 主表与子表是否一对多
//     *
//     * true：一对多
//     * false：一对一
//     */
//    private Boolean subJoinMany;

    // ========== 树表相关字段 ==========

    /**
     * 树表的父字段编号
     */
    private Long treeParentColumnId;

    /**
     * 树表的名字字段编号
     */
    private Long treeNameColumnId;
}
