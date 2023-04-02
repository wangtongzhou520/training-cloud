package org.training.cloud.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.user.SaveUserDTO;
import org.training.cloud.system.dto.user.UpdateUserDTO;
import org.training.cloud.system.service.user.UserService;
import org.training.cloud.system.vo.user.SysUserVO;

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
    private UserService userService;

//    /**
//     * 保存用户信息
//     *
//     * @param saveUserDTO saveUserDTO
//     * @return true or false
//     */
//    @PostMapping("/user")
//    @ApiOperation("添加用户信息")
//    public CommonResponse<?> saveUser(@RequestBody @Validated SaveUserDTO saveUserDTO) {
//        userService.saveUser(saveUserDTO);
//        return CommonResponse.ok();
//    }
//
//    /**
//     * 更新用户信息
//     *
//     * @param updateUserDTO updateUserDTO
//     * @return true or false
//     */
//    @PutMapping("/user")
//    @ApiOperation("添加用户信息")
//    public CommonResponse<?> updateUser(@RequestBody @Validated UpdateUserDTO updateUserDTO) {
//        userService.updateUser(updateUserDTO);
//        return CommonResponse.ok();
//    }
//
//    /**
//     * 分页查询用户信息
//     *
//     * @param deptId   deptId
//     * @param pageSize pageSize
//     * @param pageNo   pageNo
//     * @return 分页用户信息
//     */
//    @GetMapping("/users/{deptId}")
//    @ApiOperation("分页查询用户信息")
//    public CommonResponse<PageResponse<SysUserVO>> queryPage(@PathVariable String deptId,
//                                                             Integer pageSize, Integer pageNo) {
////        Long total = userService.countSysUsersByDeptId(deptId);
////        List<SysUserVO> sysUserVOList = Lists.newArrayList();
////        if (total > 0) {
////            PageParam pageParam =
////                    new PageParam().setPageSize(pageSize).setPageNo(pageNo);
////            List<SysUserVO> sysUserBOList =
////                    userService.querySysUsersByDeptId(deptId, pageParam);
////        }
////        PageResponse<SysUserVO> response =
////                PageResponse.pageResponse(sysUserVOList, total);
////        return CommonResponse.ok(response);
//        return null;
//    }
}
