package org.training.cloud.system.controller.admin.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.dto.permission.AddMenuDTO;
import org.training.cloud.system.dto.permission.MenuDTO;
import org.training.cloud.system.dto.permission.ModifyMenuDTO;
import org.training.cloud.system.service.permission.MenuService;
import org.training.cloud.system.vo.permission.MenuVO;

import javax.annotation.Resource;
import java.util.List;

/**
 * 菜单
 *
 * @author wangtongzhou
 * @since 2023-05-30 21:58
 */
@RestController
@RequestMapping("/sys/admin/")
@Tag(name = "菜单相关接口")
public class MenuController {

    @Resource
    private MenuService menuService;

    @PostMapping("/menu")
    @Operation(summary = "添加菜单信息")
    public CommonResponse<?> addMenu(@RequestBody @Validated AddMenuDTO addMenuDTO) {
        menuService.addMenu(addMenuDTO);
        return CommonResponse.ok();
    }


    @PutMapping("/menu")
    @Operation(summary = "修改菜单信息")
    public CommonResponse<?> modifyMenu(@RequestBody @Validated ModifyMenuDTO modifyMenuDTO) {
        menuService.modifyMenu(modifyMenuDTO);
        return CommonResponse.ok();
    }


    @DeleteMapping("/menu")
    @Operation(summary = "删除菜单信息")
    public CommonResponse<?> removeMenu(@RequestParam("id") Long id) {
        menuService.removeMenu(id);
        return CommonResponse.ok();
    }


    @GetMapping("/list")
    @Operation(summary = "菜单信息")
    public CommonResponse<List<MenuVO>> menuList(MenuDTO menuDTO) {
        return CommonResponse.ok();
    }

}
