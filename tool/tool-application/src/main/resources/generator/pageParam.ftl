package ${basePackage}.${table.moduleName}.dto.${table.businessName}


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import org.training.cloud.common.core.vo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;


import static ${DateUtils}.DATE_TIME_PATTERN;


@Data
@Accessors(chain = true)
@Schema(description = "${table.classComment}分页查询")
public class ${table.className}DTO extends PageParam {


#foreach ($column in $columns)
    ##查询操作
    #if (${column.queryResultField})
        ##时间范围查询
        #if (${column.queryConditionField} == "BETWEEN")
        @Schema(description = "${column.columnComment}"#if ("$!column.example" != ""), example = "${column.example}"#end)
        @DateTimeFormat(pattern = DATE_TIME_PATTERN)
        private ${column.javaType}[] ${column.javaField};
        #else
        @Schema(description = "${column.columnComment}"#if ("$!column.example" != ""), example = "${column.example}"#end)
        private ${column.javaType} ${column.javaField};
        #end
    #end
#end


}


