package org.training.cloud.system.service.auth;

import org.training.cloud.system.dto.auth.AuthLoginDTO;
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
}
