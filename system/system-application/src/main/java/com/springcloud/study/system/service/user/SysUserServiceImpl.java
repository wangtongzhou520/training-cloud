package com.springcloud.study.system.service.user;

import com.google.common.base.Preconditions;
import com.springcloud.study.common.core.exception.BusinessException;
import com.springcloud.study.common.core.util.MD5Util;
import com.springcloud.study.common.core.vo.PageParam;
import com.springcloud.study.system.convert.user.SysUserConvert;
import com.springcloud.study.system.dao.user.SysUserMapper;
import com.springcloud.study.system.dto.user.SaveUserDTO;
import com.springcloud.study.system.dto.user.UpdateUserDTO;
import com.springcloud.study.system.entity.user.SysUser;
import com.springcloud.study.system.vo.user.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户实现类
 *
 * @author wangtongzhou
 * @since 2020-08-13 21:31
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public void saveUser(SaveUserDTO saveUserDTO) {
        if (checkTelephoneExist(saveUserDTO.getTelephone(), saveUserDTO.getId())) {
            throw new BusinessException("电话已被占用");
        }
        if (checkEmailExist(saveUserDTO.getMail(), saveUserDTO.getId())) {
            throw new BusinessException("邮箱已经被占用");
        }
        String encryptedPassword = MD5Util.encrypt("123456");
        SysUser sysUser = SysUserConvert.INSTANCE.convert(saveUserDTO);
        sysUser.setCreateOperator("")
                .setPassword(encryptedPassword)
                .setModifiedOperatorIp("")
                .setModifiedOperator("")
                .setGmtCreate(new Date())
                .setGmtModified(new Date());
        sysUserMapper.insert(sysUser);
    }

    @Override
    public void updateUser(UpdateUserDTO updateUserDTO) {
        if (checkTelephoneExist(updateUserDTO.getTelephone(), updateUserDTO.getId())) {
            throw new BusinessException("电话已被占用");
        }
        if (checkEmailExist(updateUserDTO.getMail(), updateUserDTO.getId())) {
            throw new BusinessException("邮箱已经被占用");
        }
        SysUser before = sysUserMapper.selectById(updateUserDTO.getId());
        Preconditions.checkNotNull(before, "待更新用户信息不存在");
        SysUser after = SysUserConvert.INSTANCE.convert(updateUserDTO);
        after.setModifiedOperator("")
                .setModifiedOperatorIp("")
                .setGmtModified(new Date());
        sysUserMapper.updateById(after);
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
    public int countSysUsersByDeptId(String deptId) {
        return sysUserMapper.countByDeptId(deptId);
    }

    private boolean checkEmailExist(String mail, Integer id) {
        return sysUserMapper.countByMail(mail, id) > 0;
    }

    private boolean checkTelephoneExist(String telephone, Integer id) {
        return sysUserMapper.countByTelephone(telephone, id) > 0;
    }
}
