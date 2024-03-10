package org.training.cloud.tool.controller.admin.dataSourceConfig;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.tool.convert.db.DataSourceConfigConvert;
import org.training.cloud.tool.dto.db.AddDataSourceConfigDTO;
import org.training.cloud.tool.dto.db.DataSourceConfigDTO;
import org.training.cloud.tool.dto.db.ModifyDataSourceConfigDTO;
import org.training.cloud.tool.entity.db.ToolDataSourceConfig;
import org.training.cloud.tool.service.db.DataSourceConfigService;
import org.training.cloud.tool.vo.db.DataSourceConfigVO;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 数据源配置
 *
 * @author wangtongzhou
 * @since 2024-02-26 21:08
 */

@RestController
@RequestMapping("/tool")
@Tag(name = "数据源配置")
public class DataSourceConfigController {

    @Resource
    private DataSourceConfigService dataSourceConfigService;


    /**
     * 添加数据源配置
     *
     * @param addDataSourceConfigDTO
     * @return true or false
     */
    @PostMapping("/dataSourceConfig")
    @Operation(summary = "添加数据源配置")
    public CommonResponse<?> addDataSourceConfig(@RequestBody @Valid AddDataSourceConfigDTO addDataSourceConfigDTO) {
        dataSourceConfigService.addDataSourceConfig(addDataSourceConfigDTO);
        return CommonResponse.ok();
    }

    /**
     * 更新数据源
     *
     * @param modifyDataSourceConfigDTO
     * @return
     */
    @PutMapping("/dataSourceConfig")
    @Operation(summary = "更新数据源配置")
    public CommonResponse<?> updateDataSourceConfig(@RequestBody @Valid ModifyDataSourceConfigDTO modifyDataSourceConfigDTO) {
        dataSourceConfigService.updateDataSourceConfig(modifyDataSourceConfigDTO);
        return CommonResponse.ok();
    }


    /**
     * 分页查询数据源配置
     *
     * @return 分页查询数据源配置
     */
    @PostMapping("/dataSourceConfig/page")
    @Operation(summary = "分页查询数据源配置")
    public CommonResponse<PageResponse<DataSourceConfigVO>> pageAdminUser(@RequestBody DataSourceConfigDTO dataSourceConfigDTO) {
        PageResponse<DataSourceConfigVO> pageResponse =
                dataSourceConfigService.pageDataSourceConfig(dataSourceConfigDTO);
        return CommonResponse.ok(pageResponse);
    }


    /**
     * 删除数据源配置
     *
     * @param id
     * @return
     */
    @DeleteMapping("/dataSourceConfig/{id}")
    @Operation(summary = "删除数据源配置")
    public CommonResponse<?> deleteDataSourceConfig(@PathVariable("id") Long id) {
        dataSourceConfigService.deleteDataSourceConfig(id);
        return CommonResponse.ok();
    }



    @GetMapping("/dataSourceConfig/id")
    @Operation(summary = "获得数据源配置")
    public CommonResponse<DataSourceConfigVO> getDataSourceConfig(@PathVariable("id") Long id) {
        ToolDataSourceConfig config = dataSourceConfigService.getDataSourceConfig(id);
        return CommonResponse.ok(DataSourceConfigConvert.INSTANCE.convert(config));

    }



}
