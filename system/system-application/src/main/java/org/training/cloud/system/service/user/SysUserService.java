package org.training.cloud.system.service.user;

import org.training.cloud.common.core.vo.PageParam;
import org.training.cloud.system.dto.user.SaveUserDTO;
import org.training.cloud.system.dto.user.UpdateUserDTO;
import org.training.cloud.system.vo.user.SysUserVO;

import java.util.List;

/**
 * 用户相关
 *
 * @author wangtongzhou
 * @since 2020-08-13 21:23
 */
public interface SysUserService {

    /**
     * 保存用户信息
     *
     * @param saveUserDTO saveUserDTO
     */
    void saveUser(SaveUserDTO saveUserDTO);

    /**
     * 更新用户信息
     *
     * @param updateUserDTO updateUserDTO
     */
    void updateUser(UpdateUserDTO updateUserDTO);

    /**
     * 根据用户名查询用户信息
     *
     * @param userName userName
     * @return 用户信息
     */
    SysUserVO querySysByUserName(String userName);

    /**
     * 分页查询用户信息
     *
     * @param deptId    deptId
     * @param pageParam pageParam
     * @return 用户分页信息
     */
    List<SysUserVO> querySysUsersByDeptId(String deptId,
                                          PageParam pageParam);

    /**
     * 部门id
     *
     * @param deptId deptId
     * @return 总数
     */
    Long countSysUsersByDeptId(String deptId);
}
