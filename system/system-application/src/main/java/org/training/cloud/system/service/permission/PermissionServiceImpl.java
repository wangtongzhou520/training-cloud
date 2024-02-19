package org.training.cloud.system.service.permission;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.system.dao.permission.SysRoleMenuMapper;
import org.training.cloud.system.dao.permission.SysUserRoleMapper;
import org.training.cloud.system.dto.permission.MenuDTO;
import org.training.cloud.system.entity.permission.SysMenu;
import org.training.cloud.system.entity.permission.SysRoleMenu;
import org.training.cloud.system.entity.permission.SysUserRole;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 用户角色信息
 *
 * @author wangtongzhou
 * @since 2023-05-28 19:01
 */
@Service
public class PermissionServiceImpl implements PermissionService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private RoleService roleService;

    @Resource
    private MenuService menuService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserRole(Long userId, List<Long> roleIds) {
        //查询用户已拥有的角色信息
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByUserId(userId);
        Set<Long> hasRoles = CollectionExtUtils.convertSet(sysUserRoles, SysUserRole::getRoleId);
        Set<Long> paramsRoles = new HashSet<>(roleIds);
        //查找新增和删除的角色信息
        //新增角色 出入的和已拥有的求差集
        Set<Long> result = Sets.newHashSet();
        result.addAll(paramsRoles);
        result.removeAll(hasRoles);
        //批量插入
        if (CollectionUtils.isNotEmpty(result)) {
            sysUserRoleMapper.insertBatch(CollectionExtUtils.convertList(result, x -> {
                SysUserRole sysUserRole = new SysUserRole();
                sysUserRole.setRoleId(x);
                sysUserRole.setUserId(userId);
                return sysUserRole;
            }));
        }
        //删除
        //已拥有的角色和新增角色做差集
        result.clear();
        result.addAll(hasRoles);
        result.removeAll(paramsRoles);
        if (CollectionUtils.isNotEmpty(result)) {
            sysUserRoleMapper.removeByUserIdAndRoleIds(userId, result);
        }
    }

    @Override
    public void removeUserRole(Long userId) {
        sysUserRoleMapper.deleteListByUserId(userId);
    }

    @Override
    public Set<Long> getRoleIdListByUserId(Long userId) {
        List<SysUserRole> sysUserRoles = sysUserRoleMapper.selectByUserId(userId);
        if (CollectionUtils.isEmpty(sysUserRoles)) {
            return Collections.emptySet();
        }
        return sysUserRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getMenuIdListByRoleId(Long roleId) {
        if (roleService.hasAnySuperAdmin(Collections.singletonList(roleId))) {
            List<SysMenu> menuList = menuService.menuList(new MenuDTO());
            return menuList.stream().map(SysMenu::getId).collect(Collectors.toSet());
        }
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByRoleId(roleId);
        if (CollectionUtils.isEmpty(sysRoleMenuList)) {
            return null;
        }
        return sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> getMenuIdListByRoleIds(Set<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.emptySet();
        }
        List<SysRoleMenu> sysRoleMenuList = sysRoleMenuMapper.selectByRoleIds(roleIds);
        return sysRoleMenuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toSet());
    }

    @Override
    public void addRoleMenu(Long roleId, List<Long> menuIds) {
        //查询用户已拥菜单信息
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectByRoleId(roleId);
        Set<Long> hasMenus = CollectionExtUtils.convertSet(sysRoleMenus,
                SysRoleMenu::getMenuId);
        Set<Long> paramsMenus = new HashSet<>(menuIds);
        //查找新增和删除的菜单信息
        //新增菜单和已拥有的求差集
        Set<Long> result = Sets.newHashSet();
        result.addAll(paramsMenus);
        result.removeAll(hasMenus);
        //批量插入
        if (CollectionUtils.isNotEmpty(result)) {
            sysRoleMenuMapper.insertBatch(CollectionExtUtils.convertList(result, x -> {
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setRoleId(roleId);
                sysRoleMenu.setMenuId(x);
                return sysRoleMenu;
            }));
        }
        //删除
        //已拥有的角色和新增角色做差集
        result.clear();
        result.addAll(hasMenus);
        result.removeAll(paramsMenus);
        if (CollectionUtils.isNotEmpty(result)) {
            sysRoleMenuMapper.removeByRoleIdAndMenuIds(roleId, result);
        }
    }
}
