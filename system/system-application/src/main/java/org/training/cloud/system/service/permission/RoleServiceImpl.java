package org.training.cloud.system.service.permission;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.permission.RoleConvert;
import org.training.cloud.system.dao.permission.SysRoleMapper;
import org.training.cloud.system.dto.permission.AddRoleDTO;
import org.training.cloud.system.dto.permission.ModifyRoleDTO;
import org.training.cloud.system.dto.permission.RoleDTO;
import org.training.cloud.system.entity.permission.SysRole;
import org.training.cloud.system.enums.permission.RoleCodeEnum;
import org.training.cloud.system.enums.permission.RoleTypeEnum;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * 角色管理
 *
 * @author wangtongzhou
 * @since 2023-05-01 20:42
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public void addRole(AddRoleDTO addRoleDTO) {
        checkRole(addRoleDTO.getName(), addRoleDTO.getCode(), null);
        SysRole sysRole = RoleConvert.INSTANCE.convert(addRoleDTO);
        sysRoleMapper.insert(sysRole);
    }

    @Override
    public void modifyRole(ModifyRoleDTO modifyRoleDTO) {
        checkExistById(modifyRoleDTO.getId());
        checkRole(modifyRoleDTO.getName(), modifyRoleDTO.getCode(), modifyRoleDTO.getId());
        SysRole sysRole = RoleConvert.INSTANCE.convert(modifyRoleDTO);
        sysRoleMapper.updateById(sysRole);
    }

    @Override
    public PageResponse<SysRole> pageRole(RoleDTO roleDTO) {
        return sysRoleMapper.selectPage(roleDTO);
    }

    @Override
    public List<SysRole> allRoles() {
        return sysRoleMapper.selectAllRoles();
    }

    @Override
    public List<SysRole> getRoleListByIds(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.emptyList();
        }
        return sysRoleMapper.selectRoleListByIds(ids);
    }

    @Override
    public void removeByRoleId(Long id) {
        checkExistById(id);
        sysRoleMapper.deleteById(id);
        //删除相关数据

    }

    @Override
    public boolean hasAnySuperAdmin(Collection<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return false;
        }
        //根据code查找超级管理员id
        SysRole sysRole = sysRoleMapper.selectByRoleCode(RoleCodeEnum.SUPER_ADMIN.getCode());
        if (Objects.isNull(sysRole)) {
            return false;
        }
        return ids.stream().anyMatch(x -> sysRole.getId().equals(x));
    }


    private void checkRole(String name, String code, Long id) {
        if (RoleCodeEnum.SUPER_ADMIN.getDesc().equals(name)) {
            throw new BusinessException(ROLE_ADMIN_CODE_ERROR);
        }
        SysRole role = sysRoleMapper.selectByRoleName(name);
        if (role != null && !role.getId().equals(id)) {
            throw new BusinessException(ROLE_NAME_EXISTS);
        }
        role = sysRoleMapper.selectByRoleCode(code);
        if (role != null && !role.getId().equals(id)) {
            throw new BusinessException(ROLE_CODE_EXISTS);
        }
    }


    private void checkExistById(Long id) {
        SysRole role = sysRoleMapper.selectById(id);
        if (Objects.isNull(role)) {
            throw new BusinessException(ROLE_NOT_EXISTS);
        }

        if (RoleTypeEnum.getByCode(role.getType()) == null) {
            throw new BusinessException(ROLE_TYPE_NOT_EXISTS);
        }
    }
}
