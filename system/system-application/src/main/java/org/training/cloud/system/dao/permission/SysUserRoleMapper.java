package org.training.cloud.system.dao.permission;

import org.mapstruct.Mapper;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.entity.permission.SysUserRole;

import java.util.Collection;
import java.util.List;

/**
 * 用户角色信息
 *
 * @author wangtongzhou
 * @since 2023-05-28 18:56
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapperExtend<SysUserRole> {

    /**
     * 根据userID查询角色信息
     *
     * @param userId
     * @return
     */
    default List<SysUserRole> selectByUserId(Long userId) {
        return selectList(SysUserRole::getUserId, userId);
    }


    /**
     * 删除
     *
     * @param userId
     * @param roleIds
     */
    default void removeByUserIdAndRoleIds(Long userId, Collection<Long> roleIds) {
        delete(new LambdaQueryWrapperExtend<SysUserRole>()
                .eq(SysUserRole::getUserId, userId)
                .in(SysUserRole::getRoleId, roleIds)
        );
    }


}
