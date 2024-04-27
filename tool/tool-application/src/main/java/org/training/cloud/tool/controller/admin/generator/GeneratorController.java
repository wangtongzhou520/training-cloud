package org.training.cloud.tool.controller.admin.generator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.convert.generator.GeneratorTableConvert;
import org.training.cloud.tool.dto.db.DatabaseTableDTO;
import org.training.cloud.tool.dto.generator.AddGeneratorDTO;
import org.training.cloud.tool.dto.generator.ModifyGeneratorDTO;
import org.training.cloud.tool.dto.generator.table.GeneratorTableDTO;
import org.training.cloud.tool.service.generator.GeneratorService;
import org.training.cloud.tool.vo.db.DatabaseTableVO;
import org.training.cloud.tool.vo.generator.GeneratorPreviewCodeVO;
import org.training.cloud.tool.vo.generator.GeneratorVO;
import org.training.cloud.tool.vo.generator.table.GeneratorTableVO;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;

import static org.training.cloud.common.web.utils.WebUtil.getLoginUserId;

/**
 * 代码生成器
 *
 * @author wangtongzhou
 * @since 2024-03-10 17:34
 */
@RestController
@RequestMapping("/tool")
@Tag(name = "代码生成器")
public class GeneratorController {

    @Resource
    private GeneratorService generatorService;

    /**
     * 创建代码生成器的表和字段定义
     *
     * @param addGeneratorDTO
     * @return
     */
    @PostMapping("/generator")
    @Operation(summary = "创建代码生成器的表和字段定义")
    @PreAuthorize("@ss.hasPermission('system:generator:create')")
    public CommonResponse<?> addGenerator(@RequestBody @Valid AddGeneratorDTO addGeneratorDTO) {
        generatorService.addGeneratorList(addGeneratorDTO);
        return CommonResponse.ok();
    }


    /**
     * 更新数据库的表和字段定义
     *
     * @param modifyGeneratorDTO
     * @return
     */
    @PutMapping("/generator")
    @Operation(summary = "更新数据库的表和字段定义")
    @PreAuthorize("@ss.hasPermission('system:generator:update')")
    public CommonResponse<?> updateGenerator(@RequestBody @Valid ModifyGeneratorDTO modifyGeneratorDTO) {
        generatorService.modifyGenerator(modifyGeneratorDTO);
        return CommonResponse.ok();
    }


    /**
     * 分页获取表定义
     *
     * @return 分页获取表定义
     */
    @PostMapping("/generator/page")
    @Operation(summary = "分页获取表定义")
    @PreAuthorize("@ss.hasPermission('system:generator:list')")
    public CommonResponse<PageResponse<GeneratorTableVO>> pageAdminUser(@RequestBody GeneratorTableDTO generatorTableDTO) {
        PageResponse<GeneratorTableVO> pageResponse =
                generatorService.pageGeneratorTable(generatorTableDTO);
        return CommonResponse.ok(pageResponse);
    }


    /**
     * 获取表和字段的详情
     *
     * @param id
     * @return
     */
    @GetMapping("/generator/detail/{id}")
    @Operation(summary = "获取表和字段的详情")
    @PreAuthorize("@ss.hasPermission('system:generator:query')")
    public CommonResponse<GeneratorVO> queryGeneratorDetail(@PathVariable("id") Long id) {
        return CommonResponse.ok(generatorService.queryGeneratorDetail(id));
    }


    @GetMapping("/generator/table/list")
    @Operation(summary = "获得数据库表,已导入的会过滤")
    @Parameters({
            @Parameter(name = "dataSourceConfigId", description = "数据源配置的编号", required = true, example = "1"),
            @Parameter(name = "name", description = "表名，模糊匹配", example = "表名"),
            @Parameter(name = "comment", description = "描述，模糊匹配", example =
                    "描述")
    })
    @PreAuthorize("@ss.hasPermission('system:generator:list')")
    public CommonResponse<List<DatabaseTableVO>> queryDatabaseTableList(
            @RequestParam(value = "dataSourceConfigId") Long dataSourceConfigId,
            @RequestParam(value = "tableName", required = false) String tableName,
            @RequestParam(value = "tableDesc", required = false) String tableDesc) {
        DatabaseTableDTO databaseTableDTO = new DatabaseTableDTO();
        databaseTableDTO.setTableName(tableName);
        databaseTableDTO.setTableDesc(tableDesc);
        databaseTableDTO.setDataSourceConfigId(dataSourceConfigId);
        return CommonResponse.ok(generatorService.queryGeneratorTableList(databaseTableDTO));
    }


    @GetMapping("/generator/preview")
    @Operation(summary = "代码预览")
    @Parameters({
            @Parameter(name = "tableId", description = "表单ID", required = true, example = "1"),
    })
    @PreAuthorize("@ss.hasPermission('system:generator:query')")
    public CommonResponse<List<GeneratorPreviewCodeVO>> previewCodegen(@RequestParam("tableId") Long tableId) {
        Map<String, String> codeMap = generatorService.previewGeneratorCode(tableId);
        return CommonResponse.ok(GeneratorTableConvert.INSTANCE.convert(codeMap));
    }


}
