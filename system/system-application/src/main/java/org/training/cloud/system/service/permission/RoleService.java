package org.training.cloud.system.service.permission;

import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.dto.permission.AddRoleDTO;
import org.training.cloud.system.dto.permission.ModifyRoleDTO;
import org.training.cloud.system.dto.permission.RoleDTO;
import org.training.cloud.system.entity.permission.SysRole;

import java.util.Collection;
import java.util.List;

/**
 * 角色管理
 *
 * @author wangtongzhou
 * @since 2023-05-01 20:41
 */
public interface RoleService {

    /**
     * 新增角色
     *
     * @param addRoleDTO
     */
    void addRole(AddRoleDTO addRoleDTO);

    /**
     * 修改角色
     *
     * @param modifyRoleDTO
     */
    void modifyRole(ModifyRoleDTO modifyRoleDTO);

    /**
     * 分页查询
     *
     * @param roleDTO
     */
    PageResponse<SysRole> pageRole(RoleDTO roleDTO);

    /**
     * 获取所有的角色信息
     *
     * @return
     */
    List<SysRole> allRoles();

    /**
     * 根据角色ID查询角色信息
     *
     * @param ids
     * @return
     */
    List<SysRole> getRoleListByIds(Collection<Long> ids);


    /**
     * 删除角色
     *
     * @param id
     */
    void removeByRoleId(Long id);


    /**
     * 判断是否是超级管理员
     *
     * @param ids
     * @return
     */
    boolean hasAnySuperAdmin(Collection<Long> ids);





}
