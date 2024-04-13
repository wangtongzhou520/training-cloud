package ${basePackage}.${table.moduleName}.entity.${table.businessName}


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ${BaseDO};

<#list columns as column>
    <#if column.javaType == "BigDecimal">
        import java.math.BigDecimal;
        <#break>
    </#if>
</#list>

<#list columns as column>
    <#if column.javaType == "LocalDateTime">
        import java.time.LocalDateTime;
        <#break>
    </#if>
</#list>


@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "${table.tableName}", autoResultMap = true)
@Accessors(chain = true)
public class ${table.className} extends BaseDO {
<#list columns as column>
    <#assign excludedFields = ["gmtCreate", "gmtModified", "createOperator", "modifiedOperator", "deleteState"]>
    <#if !excludedFields?seq_contains(column.javaField) >
        /**
         * ${column.columnComment}
        <#if column.dictType != "">
            *
            * 枚举 {@link TODO ${column.dictType} 对应的类}
        </#if>
         */
        <#if column.primaryKey >
            @TableId <#if column.javaType == 'String'> (type = IdType.INPUT) </#if>
        </#if>
         private ${column.javaType} ${column.javaField};
    </#if>
</#list>

}







