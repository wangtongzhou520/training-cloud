package org.training.cloud.system.dao.permission;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.dto.permission.RoleDTO;
import org.training.cloud.system.entity.permission.SysRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色
 *
 * @author wangtongzhou
 * @since 2023-01-13 18:05
 */
@Mapper
public interface SysRoleMapper extends BaseMapperExtend<SysRole> {

    /**
     * 根据角色名称查询
     *
     * @param roleName
     * @return
     */
    default SysRole selectByRoleName(String roleName) {
        return selectOne(SysRole::getName, roleName);
    }


    /**
     * 根据角色查询code
     *
     * @param code
     * @return
     */
    default SysRole selectByRoleCode(String code) {
        return selectOne(SysRole::getCode, code);
    }


    /**
     * 分页查询
     *
     * @param roleDTO
     * @return
     */
    default PageResponse<SysRole> selectPage(RoleDTO roleDTO) {
        return selectPage(roleDTO, new LambdaQueryWrapperExtend<SysRole>()
                .likeIfPresent(SysRole::getName, roleDTO.getName())
                .eqIfPresent(SysRole::getType, roleDTO.getType())
        );
    }
}