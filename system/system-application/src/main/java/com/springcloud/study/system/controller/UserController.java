package com.springcloud.study.system.controller;

import com.google.common.collect.Lists;
import com.springcloud.study.common.core.vo.CommonResponse;
import com.springcloud.study.common.core.vo.PageParam;
import com.springcloud.study.common.core.vo.PageResponse;
import com.springcloud.study.system.bo.user.SysUserBO;
import com.springcloud.study.system.convert.user.SysUserConvert;
import com.springcloud.study.system.dto.user.SaveUserDTO;
import com.springcloud.study.system.dto.user.UpdateUserDTO;
import com.springcloud.study.system.service.user.SysUserService;
import com.springcloud.study.system.vo.user.SysUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户相关接口
 *
 * @author wangtongzhou
 * @since 2020-08-13 18:49
 */
@RestController
@RequestMapping("/sys")
@Api("部门信息")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存用户信息
     *
     * @param saveUserDTO saveUserDTO
     * @return true or false
     */
    @PostMapping("/user")
    @ApiOperation("添加用户信息")
    public CommonResponse<?> saveUser(@RequestBody @Validated SaveUserDTO saveUserDTO) {
        sysUserService.saveUser(saveUserDTO);
        return CommonResponse.ok();
    }

    /**
     * 更新用户信息
     *
     * @param updateUserDTO updateUserDTO
     * @return true or false
     */
    @PutMapping("/user")
    @ApiOperation("添加用户信息")
    public CommonResponse<?> updateUser(@RequestBody @Validated UpdateUserDTO updateUserDTO) {
        sysUserService.updateUser(updateUserDTO);
        return CommonResponse.ok();
    }

    /**
     * 分页查询用户信息
     *
     * @param deptId   deptId
     * @param pageSize pageSize
     * @param pageNo   pageNo
     * @return 分页用户信息
     */
    @GetMapping("/users/{deptId}")
    @ApiOperation("分页查询用户信息")
    public CommonResponse<PageResponse<SysUserVO>> queryPage(@PathVariable String deptId,
                                                             Integer pageSize, Integer pageNo) {
        int total = sysUserService.countSysUsersByDeptId(deptId);
        List<SysUserVO> sysUserVOList = Lists.newArrayList();
        if (total > 0) {
            PageParam pageParam =
                    new PageParam().setPageSize(pageSize).setPageNo(pageNo);
            List<SysUserBO> sysUserBOList =
                    sysUserService.querySysUsersByDeptId(deptId, pageParam);
            sysUserVOList =
                    SysUserConvert.INSTANCE.convertVO(sysUserBOList);
        }
        PageResponse<SysUserVO> response =
                PageResponse.pageResponse(sysUserVOList, total);
        return CommonResponse.ok(response);
    }
}
