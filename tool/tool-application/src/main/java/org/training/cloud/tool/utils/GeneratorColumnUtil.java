package org.training.cloud.tool.utils;

import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.training.cloud.tool.convert.generator.GeneratorColumnConvert;
import org.training.cloud.tool.entity.generator.ToolGeneratorColumn;
import org.training.cloud.tool.enums.generator.GeneratorColumnHtmlTypeEnum;
import org.training.cloud.tool.enums.generator.GeneratorColumnListConditionEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 生成列工具类
 *
 * @author wangtongzhou
 * @since 2024-03-03 16:23
 */
public class GeneratorColumnUtil {


    /**
     * 字段查询映射
     */
    private static final Map<String, GeneratorColumnListConditionEnum> COLUMN_LIST_OPERATION_CONDITION_MAPPINGS;

    /**
     * 字段页面展示映射
     */
    private static final Map<String, GeneratorColumnHtmlTypeEnum> COLUMN_HTML_TYPE_MAPPINGS;


    static {
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS = Maps.newHashMap();
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS.put("name", GeneratorColumnListConditionEnum.LIKE);
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS.put("time", GeneratorColumnListConditionEnum.BETWEEN);
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS.put("date", GeneratorColumnListConditionEnum.BETWEEN);

        COLUMN_HTML_TYPE_MAPPINGS = Maps.newHashMap();
        COLUMN_HTML_TYPE_MAPPINGS.put("status", GeneratorColumnHtmlTypeEnum.RADIO);
        COLUMN_HTML_TYPE_MAPPINGS.put("sex", GeneratorColumnHtmlTypeEnum.RADIO);
        COLUMN_HTML_TYPE_MAPPINGS.put("type", GeneratorColumnHtmlTypeEnum.SELECT);
        COLUMN_HTML_TYPE_MAPPINGS.put("image", GeneratorColumnHtmlTypeEnum.IMAGE_UPLOAD);
        COLUMN_HTML_TYPE_MAPPINGS.put("file", GeneratorColumnHtmlTypeEnum.FILE_UPLOAD);
        COLUMN_HTML_TYPE_MAPPINGS.put("content", GeneratorColumnHtmlTypeEnum.EDITOR);
        COLUMN_HTML_TYPE_MAPPINGS.put("description", GeneratorColumnHtmlTypeEnum.EDITOR);
        COLUMN_HTML_TYPE_MAPPINGS.put("demo", GeneratorColumnHtmlTypeEnum.EDITOR);
        COLUMN_HTML_TYPE_MAPPINGS.put("time", GeneratorColumnHtmlTypeEnum.DATETIME);
        COLUMN_HTML_TYPE_MAPPINGS.put("date", GeneratorColumnHtmlTypeEnum.DATETIME);
    }


    /**
     * 新增操作，不需要传递的字段
     */
    private static final Set<String> CREATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 修改操作，不需要传递的字段
     */
    private static final Set<String> UPDATE_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet();
    /**
     * 列表操作的条件，不需要传递的字段
     */
    private static final Set<String> LIST_OPERATION_EXCLUDE_COLUMN = Sets.newHashSet("id");
    /**
     * 列表操作的结果，不需要返回的字段
     */
    private static final Set<String> LIST_OPERATION_RESULT_EXCLUDE_COLUMN = Sets.newHashSet();


    public static List<ToolGeneratorColumn> buildColumns(Long tableId,
                                                  List<TableField> tableFields) {
        List<ToolGeneratorColumn> columns = GeneratorColumnConvert.INSTANCE.convertList(tableFields);
        int index = 1;
        for (ToolGeneratorColumn column : columns) {
            column.setTableId(tableId);
            column.setOrdinalPosition(index++);
            // 特殊处理：Byte => Integer
            if (Byte.class.getSimpleName().equals(column.getJavaType())) {
                column.setJavaType(Integer.class.getSimpleName());
            }
            //处理查询
            processColumnOperation(column);
            //字段映射的UI
            processColumnUI(column);
            //swagger example
            processColumnSwaggerExample(column);
        }
        return columns;
    }


    private static void processColumnOperation(ToolGeneratorColumn column) {
        // 处理 createOperation 字段
        column.setCreateOperation(!CREATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，创建时无需传递
        // 处理 updateOperation 字段
        column.setUpdateOperation(!UPDATE_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                || column.getPrimaryKey()); // 对于主键，更新时需要传递
        // 处理 listOperation 字段
        column.setListOperation(!LIST_OPERATION_EXCLUDE_COLUMN.contains(column.getJavaField())
                && !column.getPrimaryKey()); // 对于主键，列表过滤不需要传递
        // 处理 listOperationCondition 字段
        COLUMN_LIST_OPERATION_CONDITION_MAPPINGS.entrySet().stream()
                .filter(entry -> StringUtils.endsWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setListOperationCondition(entry.getValue().getCondition()));
        if (column.getListOperationCondition() == null) {
            column.setListOperationCondition(GeneratorColumnListConditionEnum.EQ.getCondition());
        }
        // 处理 listOperationResult 字段
        column.setListOperationResult(!LIST_OPERATION_RESULT_EXCLUDE_COLUMN.contains(column.getJavaField()));
    }


    private static void processColumnUI(ToolGeneratorColumn column) {
        // 基于后缀进行匹配
        COLUMN_HTML_TYPE_MAPPINGS.entrySet().stream()
                .filter(entry -> StringUtils.endsWithIgnoreCase(column.getJavaField(), entry.getKey()))
                .findFirst().ifPresent(entry -> column.setHtmlType(entry.getValue().getType()));
        // 如果是 Boolean 类型时，设置为 radio 类型.
        if (Boolean.class.getSimpleName().equals(column.getJavaType())) {
            column.setHtmlType(GeneratorColumnHtmlTypeEnum.RADIO.getType());
        }
        // 如果是 LocalDateTime 类型，则设置为 datetime 类型
        if (LocalDateTime.class.getSimpleName().equals(column.getJavaType())) {
            column.setHtmlType(GeneratorColumnHtmlTypeEnum.DATETIME.getType());
        }
        //设置默认为input类型
        if (column.getHtmlType() == null) {
            column.setHtmlType(GeneratorColumnHtmlTypeEnum.INPUT.getType());
        }
    }


    private static void processColumnSwaggerExample(ToolGeneratorColumn column) {

        // status 状态值
        if (StringUtils.endsWithIgnoreCase(column.getJavaField(), "status")) {
            column.setExample("1");
        }

        // type 枚举值
        if (StringUtils.endsWithIgnoreCase(column.getJavaField(), "type")) {
            column.setExample("2");
        }

        // url
        if (StringUtils.endsWithIgnoreCase(column.getColumnName(), "url")) {
            column.setExample("https://baidu.com");
        }

    }
}
