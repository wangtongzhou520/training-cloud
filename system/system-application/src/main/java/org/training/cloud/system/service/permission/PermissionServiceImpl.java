package org.training.cloud.system.service.permission;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.system.dao.permission.SysUserRoleMapper;
import org.training.cloud.system.entity.permission.SysUserRole;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        if (CollectionUtils.isNotEmpty(result)){
            sysUserRoleMapper.removeByUserIdAndRoleIds(userId,result);
        }
    }
}
