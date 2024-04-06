package ${basePackage}.${table.moduleName}.vo.${table.businessName}

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;
##BigDecimal
#foreach ($column in $columns)
    #if (${column.queryField} && ${column.javaType} == "BigDecimal")
    import java.math.BigDecimal;
        #break
    #end
#end
##LocalDateTime
#foreach ($column in $columns)
    #if (${column.queryField} && ${column.javaType} == "LocalDateTime")
    import org.springframework.format.annotation.DateTimeFormat;
    import java.time.LocalDateTime;
        #break
    #end
#end


import static ${DateUtils}.DATE_TIME_PATTERN;


@Data
@Accessors(chain = true)
@Schema(description = "${table.classComment}返回信息")
public class ${table.className}VO implements Serializable {
#foreach ($column in $columns)
    ##返回操作
    #if (${column.queryField})
    @Schema(description = "${column.columnComment}"#if (
        "$!column.example" != ""), example = "${column.example}"#end)
    private ${column.javaType} ${column.javaField};
    #end
#end


}
