package org.training.cloud.system.dao.permission;

import org.apache.ibatis.annotations.Mapper;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.system.dto.permission.MenuDTO;
import org.training.cloud.system.entity.permission.SysMenu;

import java.util.Collection;
import java.util.List;

/**
 * 菜单
 *
 * @author wangtongzhou
 * @since 2023-05-30 21:57
 */
@Mapper
public interface SysMenuMapper extends BaseMapperExtend<SysMenu> {


    /**
     * 更加父Id和菜单名称查询
     *
     * @param parentId
     * @param name
     * @return
     */
    default SysMenu selectByParentIdAndMenuName(Long parentId, String name) {
        return selectOne(new LambdaQueryWrapperExtend<SysMenu>()
                .eq(SysMenu::getParentId, parentId)
                .eq(SysMenu::getName, name)
        );
    }


    /**
     * 根据菜单列表获取所有的菜单信息
     *
     * @param ids
     * @return
     */
    default List<SysMenu> selectMenuListByIds(Collection<Long> ids) {
        return selectList(SysMenu::getId, ids);
    }

    /**
     * 查询菜单下面是子菜单个数
     *
     * @param parentId
     * @return
     */
    default Long selectCountByParentId(Long parentId) {
        return selectCount(SysMenu::getParentId, parentId);
    }

    /**
     * 查询所有的菜单信息
     *
     * @param menuDTO
     * @return
     */
    default List<SysMenu> selectList(MenuDTO menuDTO) {
        return selectList(new LambdaQueryWrapperExtend<SysMenu>()
                .likeIfPresent(SysMenu::getName, menuDTO.getName())
        );
    }
}
