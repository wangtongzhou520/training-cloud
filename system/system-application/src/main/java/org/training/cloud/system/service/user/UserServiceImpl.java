package org.training.cloud.system.service.user;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.user.UserConvert;
import org.training.cloud.system.dao.user.SysUserMapper;
import org.training.cloud.system.dto.admin.user.AddAdminUserDTO;
import org.training.cloud.system.dto.admin.user.AdminUserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.enums.user.UserTypeEnum;

import java.util.Objects;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.USER_MAIL_EXISTS;
import static org.training.cloud.system.constant.SystemExceptionEnumConstants.USER_PHONE_EXISTS;

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
    public void addUser(AddAdminUserDTO addAdminUserDTO) {
        checkTelephoneExist(addAdminUserDTO.getTelephone(), null);
        checkEmailExist(addAdminUserDTO.getMail(), null);
        String encryptedPassword = ENCODER.encode(addAdminUserDTO.getPassword());
        SysUser sysUser = UserConvert.INSTANCE.convert(addAdminUserDTO);
        sysUser.setUserType(UserTypeEnum.ADMIN.getCode())
                .setPassword(encryptedPassword);
        sysUserMapper.insert(sysUser);
    }

//    @Override
//    public void updateUser(UpdateUserDTO updateUserDTO) {
////        if (checkTelephoneExist(updateUserDTO.getTelephone(), updateUserDTO.getId())) {
////            throw new BusinessException("电话已被占用");
////        }
////        if (checkEmailExist(updateUserDTO.getMail(), updateUserDTO.getId())) {
////            throw new BusinessException("邮箱已经被占用");
////        }
////        SysUser before = sysUserMapper.selectById(updateUserDTO.getId());
////        Preconditions.checkNotNull(before, "待更新用户信息不存在");
////        SysUser after = SysUserConvert.INSTANCE.convert(updateUserDTO);
////        after.setModifiedOperator("")
////                .setGmtModified(new Date());
////        sysUserMapper.updateById(after);
//    }

    @Override
    public SysUser querySysByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public PageResponse<SysUser> pageAdminUser(AdminUserDTO adminUserDTO) {
        return sysUserMapper.selectPage(adminUserDTO);
    }


//    @Override
//    public Long countSysUsersByDeptId(String deptId) {
//        return sysUserMapper.countByDeptId(deptId);
//    }

    @Override
    public boolean matchPassWord(String passWord, String encodedPassword) {
        return ENCODER.matches(passWord, encodedPassword);
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
