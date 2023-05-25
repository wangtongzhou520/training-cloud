package org.training.cloud.system.dao.user;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.dto.user.UserDTO;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.enums.user.UserTypeEnum;

/**
 * user mapper
 *
 * @author wangtongzhou
 * @since 2020-08-31 21:33
 */
@Mapper
public interface SysUserMapper extends BaseMapperExtend<SysUser> {


    /**
     * 根据邮箱查询用户
     *
     * @param mail
     * @return
     */
    default SysUser selectByMail(String mail) {
        return selectOne(SysUser::getMail, mail);
    }

    /**
     * 根据电话查询用户
     *
     * @param telephone
     * @return
     */
    default SysUser selectByPhone(String telephone) {
        return selectOne(SysUser::getTelephone, telephone);
    }


    /**
     * 根据用户名查询用户
     *
     * @param username
     * @return
     */
    default SysUser selectByUserName(String username) {
        return selectOne(SysUser::getUsername, username);
    }

    /**
     * 管理端分页查询
     *
     * @param userDTO
     * @return
     */
    default PageResponse<SysUser> selectPage(UserDTO userDTO) {
        return selectPage(userDTO, new LambdaQueryWrapperExtend<SysUser>()
                .likeIfPresent(SysUser::getUsername, userDTO.getUserName())
                .eqIfPresent(SysUser::getDeptId, userDTO.getDeptId())
                .eq(SysUser::getUserType, UserTypeEnum.USER.getCode())
        );
    }
}
