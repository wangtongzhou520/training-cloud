package ${basePackage}.${table.moduleName}.dto.${table.businessName}


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
##BigDecimal
#foreach ($column in $columns)
    #if (${column.addField} &&  ${column.javaType} == "BigDecimal")
    import java.math.BigDecimal;
        #break
    #end
#end
##LocalDateTime
#foreach ($column in $columns)
    #if (${column.addField} && ${column.javaType} == "LocalDateTime")
    import org.springframework.format.annotation.DateTimeFormat;
    import java.time.LocalDateTime;
        #break
    #end
#end




@Data
@Accessors(chain = true)
@Schema(description = "${table.classComment}新增")
public class Add${table.className}DTO implements Serializable {

#foreach ($column in $columns)
    #if (${column.addField})
        ##Swagger
    @Schema(description = "${column.columnComment}"#if (!${column.nullable}), requiredMode = Schema.RequiredMode.REQUIRED#end#if (
        "$!column.example" != ""), example = "${column.example}"#end)
        ##参数校验
        #if (!${column.nullable} && !${column.primaryKey})
            #if (${column.javaType} == 'String')
            @NotBlank(message = "${column.columnComment}不能为空")
            #else
            @NotNull(message = "${column.columnComment}不能为空")
            #end
        #end
    private ${column.javaType} ${column.javaField};

    #end
#end
}
