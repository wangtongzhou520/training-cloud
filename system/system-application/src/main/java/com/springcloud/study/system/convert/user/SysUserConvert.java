package com.springcloud.study.system.convert.user;

import com.springcloud.study.system.bo.user.SysUserBO;
import com.springcloud.study.system.dto.user.SaveUserDTO;
import com.springcloud.study.system.dto.user.UpdateUserDTO;
import com.springcloud.study.system.entity.user.SysUserDO;
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
    SysUserDO convert(SaveUserDTO saveUserDTO);


    /**
     * updateUserDTO convert sysUserDO
     *
     * @param updateUserDTO updateUserDTO
     * @return sysUserDO
     */
    @Mappings({})
    SysUserDO convert(UpdateUserDTO updateUserDTO);

    /**
     * 用户信息
     *
     * @param sysUserDO sysUserDO
     * @return sysUserBO
     */
    @Mappings({})
    SysUserBO convert(SysUserDO sysUserDO);

    /**
     * sysUserDOList convert  sysUserBOList
     *
     * @param sysUserDOList sysUserDOList
     * @return sysUserBOList
     */
    List<SysUserBO> convert(List<SysUserDO> sysUserDOList);

    /**
     * sysUserBO convert sysUserBO
     *
     * @param sysUserBO sysUserBO
     * @return sysUserBO
     */
    @Mappings({})
    SysUserVO convert(SysUserBO sysUserBO);

    /**
     * sysUserBOList convert sysUserVOList
     *
     * @param sysUserBOList sysUserBOList
     * @return sysUserVOList
     */
    List<SysUserVO> convertVO(List<SysUserBO> sysUserBOList);
}
