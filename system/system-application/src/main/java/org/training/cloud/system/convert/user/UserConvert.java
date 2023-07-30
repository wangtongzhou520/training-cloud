package org.training.cloud.system.convert.user;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.user.AddUserDTO;
import org.training.cloud.system.dto.user.ModifyUserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.vo.user.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

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
     * @param addUserDTO saveUserDTO
     * @return sysUserDO
     */
    @Mappings({})
    SysUser convert(AddUserDTO addUserDTO);


    /**
     * updateUserDTO convert sysUserDO
     *
     * @param modifyUserDTO updateUserDTO
     * @return sysUserDO
     */
    @Mappings({})
    SysUser convert(ModifyUserDTO modifyUserDTO);

    /**
     * 用户信息
     *
     * @param sysUser sysUserDO
     * @return sysUserBO
     */
    UserVO convert(SysUser sysUser);

    /**
     * PageResponse<SysUser> convert PageResponse<SysUserVO>
     *
     * @param pageResponse
     * @return
     */
    PageResponse<UserVO> convert(PageResponse<SysUser> pageResponse);

}
