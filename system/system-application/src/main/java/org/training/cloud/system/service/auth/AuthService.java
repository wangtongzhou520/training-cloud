package org.training.cloud.system.service.auth;

import org.training.cloud.system.dto.auth.AuthLoginDTO;
import org.training.cloud.system.dto.auth.AuthPermissionVO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.vo.auth.AuthLoginVO;

/**
 * 登录
 *
 * @author wangtongzhou 
 * @since 2023-04-01 22:05
 */
public interface AuthService {

    /**
     * 登录
     *
     * @param authLoginDTO
     * @return
     */
    AuthLoginVO login(AuthLoginDTO authLoginDTO);


    /**
     * 验证用户名和密码
     *
     * @param username
     * @param password
     * @return
     */
    SysUser authenticate(String username, String password);


    /**
     * 获取用户的权限信息
     * 
     * @param userId
     * @return
     */
    AuthPermissionVO getUserPermission(Long userId);



}
