package ${basePackage}.${table.moduleName}.dto.${table.businessName}


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
<#list columns as column>
    <#if column.modifyField && column.javaType == "BigDecimal">
import java.math.BigDecimal;
        <#break>
    </#if>
</#list>
<#list columns as column>
    <#if column.modifyField && column.javaType == "LocalDateTime">
import java.time.LocalDateTime;
        <#break>
    </#if>
</#list>

@Data
@Accessors(chain = true)
@Schema(description = "${table.classComment}修改")
public class Modify${table.className}DTO implements Serializable {

<#list columns as column>
    <#if column.modifyField>
   @Schema(description = "${column.columnComment}"<#if column.example! != "">, example = "${column.example}"</#if>)
        <#if !column.nullable && !column.primaryKey>
            <#if column.javaType == 'String'>
   @NotBlank(message = "${column.columnComment}不能为空")
            <#else>
   @NotNull(message = "${column.columnComment}不能为空")
            </#if>
        </#if>
   private ${column.javaType} ${column.javaField};

    </#if>
</#list>
}
