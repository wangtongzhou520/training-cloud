package ${basePackage}.${table.moduleName}.vo.${table.businessName}

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
<#list columns as column>
    <#if column.queryField && column.javaType == "BigDecimal">
import java.math.BigDecimal;
        <#break>
    </#if>
</#list>
<#list columns as column>
    <#if column.queryField && column.javaType == "LocalDateTime">
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
        <#break>
    </#if>
</#list>
import static ${DateUtils}.DATE_TIME_PATTERN;


@Data
@Accessors(chain = true)
@Schema(description = "${table.classComment}返回信息")
public class ${table.className}VO implements Serializable {

<#list columns as column>
    <#if column.queryField>
  @Schema(description = "${column.columnComment}"<#if column.example! != "">, example = "${column.example}"</#if>)
  private ${column.javaType} ${column.javaField};

    </#if>
</#list>


}
