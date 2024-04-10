package ${basePackage}.${table.moduleName}.entity.${table.businessName}


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ${BaseDO};

##BigDecimal
<#list columns as column>
    #if (${column.javaType} == "BigDecimal")
    import java.math.BigDecimal;
        #break
    #end
</#list>

##LocalDateTime
<#list columns as column>
    #if (${column.javaType} == "LocalDateTime")
    import java.time.LocalDateTime;
        #break
    #end
</#list>



@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "${table.tableName}", autoResultMap = true)
@Accessors(chain = true)
public class ${table.className} extends BaseDO {
<#list columns as column>
    ## 排除BaseDO
    #if (${column.javaField} != "gmtCreate" && ${column.javaField} !=
        "gmtModified" && ${column.javaField} !=
        "createOperator" && ${column.javaField} !=
        "modifiedOperator" && ${column.javaField} !=
        "deleteState")
    /**
    * ${column.columnComment}
        ##处理枚举值
        #if ("$!column.dictType" != "")
        *
        * 枚举 {@link TODO ${column.dictType} 对应的类}
        #end
    */
        #if (${column.primaryKey})
        @TableId#if (${column.javaType} == 'String')(type = IdType.INPUT)#end
        #end
    private ${column.javaType} ${column.javaField};
    #end
</#list>

}







