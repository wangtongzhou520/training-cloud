package ${basePackage}.${table.moduleName}.dto.${table.businessName}


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ${PageParam};
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


import static ${DateUtils}.DATE_TIME_PATTERN;


@Data
@Accessors(chain = true)
@Schema(description = "${table.classComment}分页查询")
public class ${table.className}DTO extends PageParam {
<#list columns as column>
    <#if column.queryResultField>
        <#if column.queryConditionField == "BETWEEN">
            @Schema(description = "${column.columnComment}"<#if column.example! != "">, example = "${column.example}"</#if>)
            @DateTimeFormat(pattern = DATE_TIME_PATTERN)
            private ${column.javaType}[] ${column.javaField};
        <#else>
            @Schema(description = "${column.columnComment}" <#if column.example! != "">, example = "${column.example}"</#if>)
            private ${column.javaType} ${column.javaField};
        </#if>

    </#if>
</#list>


}


