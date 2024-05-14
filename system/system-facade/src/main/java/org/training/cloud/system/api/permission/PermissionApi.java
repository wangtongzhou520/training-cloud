package org.training.cloud.system.api.permission;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.system.constant.ApiConstants;

/**
 * 权限相关接口
 *
 * @author wangtongzhou
 * @since 2024-05-15 06:41
 */
@FeignClient(value = ApiConstants.NAME)
public interface PermissionApi {


    String PREFIX = ApiConstants.PREFIX + "/permission";


    @GetMapping(PREFIX + "/hasAnyPermissions")
    @Operation(summary = "判断是否有权限")
    @Parameters({
            @Parameter(name = "userId", description = "用户编号", example = "1", required = true),
            @Parameter(name = "permissions", description = "权限", example = "sys:permission:list", required = true)
    })
    CommonResponse<Boolean> hasAnyPermissions(@RequestParam("userId") Long userId,
                                              @RequestParam("permissions") String... permissions);



    @GetMapping(PREFIX + "/hasAnyRoles")
    @Operation(summary = "判断是否有角色")
    @Parameters({
            @Parameter(name = "userId", description = "用户编号", example = "1", required = true),
            @Parameter(name = "roles", description = "角色", example = "admin", required = true)
    })
    CommonResponse<Boolean> hasAnyRoles(@RequestParam("userId") Long userId,
                                      @RequestParam("roles") String... roles);



}
