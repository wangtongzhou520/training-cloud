package org.training.cloud.system.service.user;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.admin.user.AddAdminUserDTO;
import org.training.cloud.system.dto.admin.user.AdminUserDTO;
import org.training.cloud.system.entity.user.SysUser;

/**
 * 用户相关
 *
 * @author wangtongzhou
 * @since 2020-08-13 21:23
 */
public interface UserService {

    /**
     * 保存用户信息
     *
     * @param addAdminUserDTO
     */
    void addUser(AddAdminUserDTO addAdminUserDTO);
//
//    /**
//     * 更新用户信息
//     *
//     * @param updateUserDTO updateUserDTO
//     */
//    void updateUser(UpdateUserDTO updateUserDTO);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName userName
     * @return 用户信息
     */
    SysUser querySysByUserName(String userName);

    /**
     * 分页查询用户信息
     *
     * @param adminUserDTO
     * @return 用户分页信息
     */
    PageResponse<SysUser> pageAdminUser(AdminUserDTO adminUserDTO);
//
//    /**
//     * 部门id
//     *
//     * @param deptId deptId
//     * @return 总数
//     */
//    Long countSysUsersByDeptId(String deptId);

    /**
     * 检查密码是否匹配
     *
     * @param passWord
     * @param encodedPassword
     * @return
     */
    boolean matchPassWord(String passWord, String encodedPassword);
}
