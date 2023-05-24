package org.training.cloud.system.convert.user;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.admin.user.AddAdminUserDTO;
import org.training.cloud.system.dto.user.UpdateUserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.vo.user.SysUserVO;
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
     * @param addAdminUserDTO saveUserDTO
     * @return sysUserDO
     */
    @Mappings({})
    SysUser convert(AddAdminUserDTO addAdminUserDTO);


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
     * PageResponse<SysUser> convert PageResponse<SysUserVO>
     *
     * @param pageResponse
     * @return
     */
    PageResponse<SysUserVO> convert(PageResponse<SysUser> pageResponse);

}
