package org.training.cloud.system.service.user;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.user.AddUserDTO;
import org.training.cloud.system.dto.user.ModifyUserDTO;
import org.training.cloud.system.dto.user.UserDTO;
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
     * @param addUserDTO
     */
    void addUser(AddUserDTO addUserDTO);

    /**
     * 更新用户信息
     *
     * @param modifyUserDTO
     */
    void updateUser(ModifyUserDTO modifyUserDTO);

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
     * @param userDTO
     * @return 用户分页信息
     */
    PageResponse<SysUser> pageAdminUser(UserDTO userDTO);

    /**
     * 删除用户
     *
     * @param id
     */
    void removeUserById(Long id);

    /**
     * 检查密码是否匹配
     *
     * @param passWord
     * @param encodedPassword
     * @return
     */
    boolean matchPassWord(String passWord, String encodedPassword);
}
