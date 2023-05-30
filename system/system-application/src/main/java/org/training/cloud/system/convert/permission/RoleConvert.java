package org.training.cloud.system.convert.permission;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.permission.AddRoleDTO;
import org.training.cloud.system.dto.permission.ModifyRoleDTO;
import org.training.cloud.system.entity.permission.SysRole;
import org.training.cloud.system.vo.permission.RoleVO;

/**
 * 角色
 *
 * @author wangtongzhou
 * @since 2023-05-28 16:43
 */
@Mapper
public interface RoleConvert {

    RoleConvert INSTANCE = Mappers.getMapper(RoleConvert.class);


    /**
     * addRoleDTO convert SysRole
     *
     * @param addRoleDTO
     * @return
     */
    @Mappings({})
    SysRole convert(AddRoleDTO addRoleDTO);

    /**
     * modifyRoleDTO convert SysRole
     *
     * @param modifyRoleDTO
     * @return
     */
    @Mappings({})
    SysRole convert(ModifyRoleDTO modifyRoleDTO);

    /**
     * sysRole convert  RoleVO
     *
     * @param sysRole
     * @return
     */
    @Mappings({})
    RoleVO convert(SysRole sysRole);

    /**
     * PageResponse<SysRole> convert PageResponse<RoleVO>
     *
     * @param pageResponse
     * @return
     */
    PageResponse<RoleVO> convert(PageResponse<SysRole> pageResponse);

}
