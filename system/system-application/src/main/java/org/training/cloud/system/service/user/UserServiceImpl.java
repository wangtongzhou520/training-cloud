package org.training.cloud.system.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.training.cloud.common.web.core.vo.PageParam;
import org.training.cloud.system.convert.user.SysUserConvert;
import org.training.cloud.system.dao.user.SysUserMapper;
import org.training.cloud.system.dto.user.SaveUserDTO;
import org.training.cloud.system.dto.user.UpdateUserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.vo.user.SysUserVO;

import javax.annotation.Resource;
import java.util.List;

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
    public void saveUser(SaveUserDTO saveUserDTO) {
//        if (checkTelephoneExist(saveUserDTO.getTelephone(),
//                saveUserDTO.getTelephone())) {
//            throw new BusinessException("电话已被占用");
//        }
//        if (checkEmailExist(saveUserDTO.getMail(), saveUserDTO.getId())) {
//            throw new BusinessException("邮箱已经被占用");
//        }
//        String encryptedPassword = MD5Util.encrypt("123456");
//        SysUser sysUser = SysUserConvert.INSTANCE.convert(saveUserDTO);
//        sysUser.setCreateOperator("")
//                .setPassword(encryptedPassword)
//                .setModifiedOperator("")
//                .setGmtCreate(new Date())
//                .setGmtModified(new Date());
//        sysUserMapper.insert(sysUser);
    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
//        if (checkTelephoneExist(updateUserDTO.getTelephone(), updateUserDTO.getId())) {
//            throw new BusinessException("电话已被占用");
//        }
//        if (checkEmailExist(updateUserDTO.getMail(), updateUserDTO.getId())) {
//            throw new BusinessException("邮箱已经被占用");
//        }
//        SysUser before = sysUserMapper.selectById(updateUserDTO.getId());
//        Preconditions.checkNotNull(before, "待更新用户信息不存在");
//        SysUser after = SysUserConvert.INSTANCE.convert(updateUserDTO);
//        after.setModifiedOperator("")
//                .setGmtModified(new Date());
//        sysUserMapper.updateById(after);
    }

    @Override
    public SysUserVO querySysByUserName(String userName) {
        SysUser sysUser = sysUserMapper.querySysByUserName(userName);
        return SysUserConvert.INSTANCE.convert(sysUser);
    }

    @Override
    public List<SysUserVO> querySysUsersByDeptId(String deptId,
                                                 PageParam pageParam) {
        List<SysUser> sysUserList =
                sysUserMapper.queryPageByDeptId(deptId, pageParam);
        List<SysUserVO> sysUserBOList =
                SysUserConvert.INSTANCE.convert(sysUserList);
        return sysUserBOList;
    }

    @Override
    public Long countSysUsersByDeptId(String deptId) {
        return sysUserMapper.countByDeptId(deptId);
    }

    @Override
    public boolean matchPassWord(String passWord, String encodedPassword) {
        return ENCODER.matches(passWord, encodedPassword);
    }

    private boolean checkEmailExist(String mail, Integer id) {
        return sysUserMapper.countByMail(mail, id) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer id) {
        return sysUserMapper.countByTelephone(telephone, id) > 0;
    }
}
