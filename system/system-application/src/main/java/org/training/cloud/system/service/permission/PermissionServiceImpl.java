package org.training.cloud.system.service.permission;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.system.dao.permission.SysRoleMenuMapper;
import org.training.cloud.system.dao.permission.SysUserRoleMapper;
import org.training.cloud.system.dto.permission.MenuDTO;
import org.training.cloud.system.entity.permission.SysMenu;
import org.training.cloud.system.entity.permission.SysRole;
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
        sysUserRoleMapper.removeListByUserId(userId);
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
    @Transactional(rollbackFor = Exception.class)
    public void addRoleMenu(Long roleId, List<Long> menuIds) {
        //查询用户已拥菜单信息
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuMapper.selectByRoleId(roleId);
        Set<Long> hasMenus = CollectionExtUtils.convertSet(sysRoleMenus, SysRoleMenu::getMenuId);
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

    @Override
    public void removeListByMenuId(Long menuId) {
        sysRoleMenuMapper.removeListByMenuId(menuId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeListByRoleId(Long roleId) {
        sysUserRoleMapper.removeListByRoleId(roleId);
        sysRoleMenuMapper.removeListByRoleId(roleId);
    }

    @Override
    public Boolean hasAnyPermissions(Long userId, String... permissions) {
        //为空说明不需要检查权限
        if (ArrayUtils.isEmpty(permissions)) {
            return true;
        }
        //后续分为两种情况管理员和普通权限用户
        //获取当前用户的角色信息
        Set<Long> roleIds = getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        //优先处理管理员权限
        Boolean hasAnySuperAdmin = roleService.hasAnySuperAdmin(roleIds);
        if (hasAnySuperAdmin) {
            return true;
        }
        //所有权限信息
        Set<Long> menuIds = getMenuIdListByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(menuIds)) {
            return false;
        }
        List<SysMenu> menus = menuService.getMenuListByIds(menuIds);
        if (CollectionUtils.isEmpty(menus)) {
            return false;
        }
        //检查用于访问权限是否全包含检查权限
        Set<String> checkPermission = new HashSet<>(Arrays.asList(permissions));
        Set<String> hasPermission = menus.stream().map(SysMenu::getPermission).collect(Collectors.toSet());

        return hasPermission.containsAll(checkPermission);
    }

    @Override
    public Boolean hasAnyRoles(Long userId, String... roles) {
        //没有角色信息不检查
        if (ArrayUtils.isEmpty(roles)) {
            return true;
        }

        Set<Long> roleIds = getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }

        List<SysRole> roleList = roleService.getRoleListByIds(roleIds);
        if (CollectionUtils.isEmpty(roleList)) {
            return false;
        }

        //检查用于访问权限是否全包含检查权限
        Set<String> checkRoles = new HashSet<>(Arrays.asList(roles));
        Set<String> hasRoles = roleList.stream().map(SysRole::getName).collect(Collectors.toSet());

        return hasRoles.containsAll(checkRoles);
    }
}
