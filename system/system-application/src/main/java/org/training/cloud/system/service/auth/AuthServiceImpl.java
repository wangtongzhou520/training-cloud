package org.training.cloud.system.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.system.constant.Oauth2ClientConstant;
import org.training.cloud.system.convert.auth.AuthConvert;
import org.training.cloud.system.convert.permission.MenuConvert;
import org.training.cloud.system.convert.user.UserConvert;
import org.training.cloud.system.dto.auth.AuthLoginDTO;
import org.training.cloud.system.dto.auth.AuthPermissionVO;
import org.training.cloud.system.dto.oauth2.AddOauth2AccessTokenDTO;
import org.training.cloud.system.entity.permission.SysMenu;
import org.training.cloud.system.entity.permission.SysRole;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.enums.user.UserStateEnum;
import org.training.cloud.system.service.oauth2.Oauth2TokenService;
import org.training.cloud.system.service.permission.MenuService;
import org.training.cloud.system.service.permission.PermissionService;
import org.training.cloud.system.service.permission.RoleService;
import org.training.cloud.system.service.user.UserService;
import org.training.cloud.system.vo.auth.AuthLoginVO;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;
import org.training.cloud.system.vo.permission.MenuVO;
import org.training.cloud.system.vo.user.UserVO;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    private PermissionService permissionService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private Oauth2TokenService auth2TokenService;

    @Override
    public AuthLoginVO login(AuthLoginDTO authLoginDTO) {
        //验证码校验
        //登录
        SysUser sysUser = authenticate(authLoginDTO.getUsername(), authLoginDTO.getPassword());
        //令牌创建
        return createToken(sysUser);
    }

    @Override
    public SysUser authenticate(String username, String password) {
        //检查账号是否存在
        SysUser sysUser = userService.querySysByUserName(username);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(AUTH_NOT_FOUND);
        }
        //匹配密码
        if (!userService.matchPassWord(password, sysUser.getPassword())) {
            throw new BusinessException(AUTH_ACCOUNT_FAIL);
        }
        //检查是否被冻结
        if (UserStateEnum.DISABLE.getCode().equals(sysUser.getStatus())) {
            throw new BusinessException(AUTH_ACCOUNT_DISABLE);
        }
        return sysUser;
    }

    @Override
    public AuthPermissionVO getUserPermission(Long userId) {

        SysUser sysUser = userService.getUserById(userId);

        Set<Long> roleIds = permissionService.getRoleIdListByUserId(userId);

        List<SysRole> sysRoles = roleService.getRoleListByIds(roleIds);

        Set<Long> permissions = permissionService.getMenuIdListByRoleIds(roleIds);

        List<SysMenu> sysMenus = menuService.getMenuListByIds(permissions);

        return AuthConvert.INSTANCE.convert(sysUser, sysRoles, sysMenus);
    }

    private AuthLoginVO createToken(SysUser sysUser) {
        //创建
        AddOauth2AccessTokenDTO addOauth2AccessTokenDTO = new AddOauth2AccessTokenDTO();
        addOauth2AccessTokenDTO.setClientId(Oauth2ClientConstant.CLIENT_ID_ADMIN);
        addOauth2AccessTokenDTO.setUserId(sysUser.getId());
        addOauth2AccessTokenDTO.setUserType(sysUser.getUserType());
        Oauth2AccessTokenVO oauth2AccessTokenVO = auth2TokenService.createAccessToken(addOauth2AccessTokenDTO);
        return AuthConvert.INSTANCE.convert(oauth2AccessTokenVO);
    }
}
