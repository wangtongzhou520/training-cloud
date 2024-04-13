package ${basePackage}.${table.moduleName}.controller.admin.${table.businessName}

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import ${CollectionExtUtils};
import ${CommonResponse};
import ${PageResponseClassName};
import ${basePackage}.${table.moduleName}.convert.${table.businessName}.${table.className}Convert;
import ${basePackage}.${table.moduleName}.dto.${table.businessName}.*;
import ${basePackage}.${table.moduleName}.service.${table.businessName}.${table.className}Service;
import ${basePackage}.${table.moduleName}.vo.${table.businessName}.${table.className}VO;


import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


@Tag(name = "管理后台${table.classComment}")
@RestController
@RequestMapping("/${table.moduleName}/${symbolCaseClassName}")
public class ${table.className}Controller {
   @Resource
   private ${table.className}Service ${firstLowerClassName}Service;

   @PostMapping("/add")
   @Operation(summary = "添加${table.classComment}信息")
   public CommonResponse<?> add${table.className}(@RequestBody @Valid Add${table.className}DTO
add${table.className}DTO) {
     ${firstLowerClassName}Service.add${table.className}(add${table.className}DTO);
     return CommonResponse.ok();
   }


   @PutMapping("/update")
   @Operation(summary = "更新${table.classComment}信息")
   public CommonResponse<?> update${table.className}(@RequestBody @Valid Modify${table.className}DTO
modify${table.className}DTO) {
     ${firstLowerClassName}Service.update${table.className}(modify${table.className}DTO);
     return CommonResponse.ok();
   }


   @GetMapping("/page")
   @Operation(summary = "分页查询${table.classComment}")
   public CommonResponse<PageResponse<${table.className}VO>> page${table.className} (@Valid ${table.className}DTO ${firstLowerClassName}DTO) {
      PageResponse<${table.className}> ${firstLowerClassName}PageResponse =${firstLowerClassName}Service.page${table.className}(${firstLowerClassName}DTO);
      return CommonResponse.ok(${table.className}Convert.INSTANCE.convert(${firstLowerClassName}PageResponse));
   }

    @GetMapping("/{id}")
    @Operation(summary = "获取${table.classComment}信息")
    public CommonResponse<?> get${table.className}ById(@PathVariable("id") Long id) {
      ${table.className} ${firstLowerClassName}=${firstLowerClassName}Service.get${table.className}ById(id);
      return CommonResponse.ok(MenuConvert.INSTANCE.convert(${firstLowerClassName}));
    }



    @DeleteMapping("/delete/{id}")
    @Operation(summary = "删除${table.classComment}")
    public CommonResponse<?> del${table.className}(@PathVariable("id") Long id) {
      ${firstLowerClassName}Service.del${table.className}(id);
      return CommonResponse.ok();
    }
}