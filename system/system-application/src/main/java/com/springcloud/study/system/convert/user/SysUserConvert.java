package com.springcloud.study.system.convert.user;

import com.springcloud.study.system.dto.user.SaveUserDTO;
import com.springcloud.study.system.dto.user.UpdateUserDTO;
import com.springcloud.study.system.entity.user.SysUser;
import com.springcloud.study.system.vo.user.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * user dto covert do
 *
 * @author wangtongzhou
 * @since 2020-08-13 22:00
 */
@Mapper
public interface SysUserConvert {

    SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);


    /**
     * saveUserDTO convert sysUserDO
     *
     * @param saveUserDTO saveUserDTO
     * @return sysUserDO
     */
    @Mappings({})
    SysUser convert(SaveUserDTO saveUserDTO);


    /**
     * updateUserDTO convert sysUserDO
     *
     * @param updateUserDTO updateUserDTO
     * @return sysUserDO
     */
    @Mappings({})
    SysUser convert(UpdateUserDTO updateUserDTO);

    /**
     * 用户信息
     *
     * @param sysUser sysUserDO
     * @return sysUserBO
     */
    @Mappings({})
    SysUserVO convert(SysUser sysUser);

    /**
     * sysUserDOList convert  sysUserBOList
     *
     * @param sysUserList sysUserDOList
     * @return sysUserBOList
     */
    List<SysUserVO> convert(List<SysUser> sysUserList);

}
