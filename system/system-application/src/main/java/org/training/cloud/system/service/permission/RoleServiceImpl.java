package org.training.cloud.system.service.permission;

import org.springframework.stereotype.Service;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.system.convert.permission.RoleConvert;
import org.training.cloud.system.dao.role.SysRoleMapper;
import org.training.cloud.system.dto.permission.AddRoleDTO;
import org.training.cloud.system.dto.permission.ModifyRoleDTO;
import org.training.cloud.system.dto.permission.RoleDTO;
import org.training.cloud.system.entity.role.SysRole;
import org.training.cloud.system.enums.permission.RoleCodeEnum;
import org.training.cloud.system.enums.permission.RoleTypeEnum;

import javax.annotation.Resource;

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
    public void removeByRoleId(Long id) {
        checkExistById(id);
        sysRoleMapper.deleteById(id);
        //删除相关数据

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

        if (RoleTypeEnum.ADMIN.getCode().equals(role.getType())) {
            throw new BusinessException(ROLE_SYSTEM_NOT_MODIFY);
        }
    }
}
