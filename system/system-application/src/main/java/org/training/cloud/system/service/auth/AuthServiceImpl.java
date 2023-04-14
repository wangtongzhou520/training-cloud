package org.training.cloud.system.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.cloud.common.web.core.exception.BusinessException;
import org.training.cloud.system.constant.Oauth2ClientConstant;
import org.training.cloud.system.dto.auth.AuthLoginDTO;
import org.training.cloud.system.dto.oauth2.AddOauth2AccessTokenDTO;
import org.training.cloud.system.enums.UserStateEnum;
import org.training.cloud.system.service.oauth2.Oauth2TokenService;
import org.training.cloud.system.service.user.UserService;
import org.training.cloud.system.vo.auth.AuthLoginVO;
import org.training.cloud.system.vo.user.SysUserVO;

import java.util.Objects;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * 登录
 *
 * @author wangtongzhou
 * @since 2023-04-02 07:50
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private Oauth2TokenService auth2TokenService;

    @Override
    public AuthLoginVO login(AuthLoginDTO authLoginDTO) {
        //登录
        //检查账号是否存在
        SysUserVO sysUserVO = userService.querySysByUserName(authLoginDTO.getUsername());
        if (Objects.isNull(sysUserVO)) {
            throw new BusinessException(AUTH_NOT_FOUND);
        }
        //匹配密码
        if (!userService.matchPassWord(authLoginDTO.getPassword(),
                sysUserVO.getPassword())) {
            throw new BusinessException(AUTH_ACCOUNT_FAIL);
        }
        //检查是否被冻结
        if (UserStateEnum.DISABLE.getCode().equals(sysUserVO.getStatus())) {
            throw new BusinessException(AUTH_ACCOUNT_DISABLE);
        }
        //令牌创建
        return createToken(sysUserVO);
    }

    private AuthLoginVO createToken(SysUserVO sysUserVO) {
        //创建
        AddOauth2AccessTokenDTO addOauth2AccessTokenDTO =
                new AddOauth2AccessTokenDTO();
        addOauth2AccessTokenDTO.setClientId(Oauth2ClientConstant.CLIENT_ID_ADMIN);
        addOauth2AccessTokenDTO.setUserId(sysUserVO.getId());
        addOauth2AccessTokenDTO.setUserType(sysUserVO.getUserType());
        auth2TokenService.createAccessToken(addOauth2AccessTokenDTO);
        return null;
    }
}
