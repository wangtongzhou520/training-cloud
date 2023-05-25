package org.training.cloud.system.service.user;

import com.google.common.base.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.user.UserConvert;
import org.training.cloud.system.dao.user.SysUserMapper;
import org.training.cloud.system.dto.user.AddUserDTO;
import org.training.cloud.system.dto.user.ModifyUserDTO;
import org.training.cloud.system.dto.user.UserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.enums.user.UserTypeEnum;

import java.util.Objects;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * 用户实现类
 *
 * @author wangtongzhou
 * @since 2020-08-13 21:31
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

    @Override
    public void addUser(AddUserDTO addUserDTO) {
        checkTelephoneExist(addUserDTO.getTelephone(), null);
        checkEmailExist(addUserDTO.getMail(), null);
        String encryptedPassword = ENCODER.encode(addUserDTO.getPassword());
        SysUser sysUser = UserConvert.INSTANCE.convert(addUserDTO);
        sysUser.setUserType(UserTypeEnum.ADMIN.getCode())
                .setPassword(encryptedPassword);
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void updateUser(ModifyUserDTO modifyUserDTO) {
        checkId(modifyUserDTO.getId());
        checkTelephoneExist(modifyUserDTO.getTelephone(), modifyUserDTO.getId());
        checkEmailExist(modifyUserDTO.getMail(), modifyUserDTO.getId());
        SysUser sysUser = UserConvert.INSTANCE.convert(modifyUserDTO);
        sysUserMapper.updateById(sysUser);
    }

    @Override
    public SysUser querySysByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public PageResponse<SysUser> pageAdminUser(UserDTO userDTO) {
        return sysUserMapper.selectPage(userDTO);
    }


//    @Override
//    public Long countSysUsersByDeptId(String deptId) {
//        return sysUserMapper.countByDeptId(deptId);
//    }

    @Override
    public boolean matchPassWord(String passWord, String encodedPassword) {
        return ENCODER.matches(passWord, encodedPassword);
    }


    private void checkId(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (Objects.isNull(sysUser)) {
            throw new BusinessException(USER_NOT_EXISTS);
        }
    }

    private void checkEmailExist(String mail, Long id) {
        SysUser sysUser = sysUserMapper.selectByMail(mail);
        if (Objects.nonNull(sysUser)) {
            if (sysUser.getId().equals(id)) {
                throw new BusinessException(USER_MAIL_EXISTS);
            }
            if (id == null) {
                throw new BusinessException(USER_MAIL_EXISTS);
            }
        }
    }

    private void checkTelephoneExist(String telephone, Long id) {
        SysUser sysUser = sysUserMapper.selectByPhone(telephone);
        if (Objects.nonNull(sysUser)) {
            if (sysUser.getId().equals(id)) {
                throw new BusinessException(USER_PHONE_EXISTS);
            }
            if (id == null) {
                throw new BusinessException(USER_PHONE_EXISTS);
            }
        }
    }
}
