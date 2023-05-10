package org.training.cloud.system.convert.user;

import org.training.cloud.system.dto.user.SaveUserDTO;
import org.training.cloud.system.dto.user.UpdateUserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.vo.user.SysUserVO;
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
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


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
