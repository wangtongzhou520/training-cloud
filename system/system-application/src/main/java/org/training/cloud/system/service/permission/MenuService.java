package org.training.cloud.system.service.permission;

import org.training.cloud.system.dto.permission.AddMenuDTO;
import org.training.cloud.system.dto.permission.MenuDTO;
import org.training.cloud.system.dto.permission.ModifyMenuDTO;
import org.training.cloud.system.entity.permission.SysMenu;
import org.training.cloud.system.vo.permission.MenuVO;

import java.util.Collection;
import java.util.List;

/**
 * 菜单
 *
 * @author wangtongzhou
 * @since 2023-05-30 21:57
 */
public interface MenuService {

    /**
     * 新增菜单
     *
     * @param addMenuDTO
     */
    void addMenu(AddMenuDTO addMenuDTO);

    /**
     * 修改菜单
     *
     * @param modifyMenuDTO
     */
    void modifyMenu(ModifyMenuDTO modifyMenuDTO);


    /**
     * 根据id获取菜单信息
     *
     * @param id
     * @return
     */
    SysMenu getMenuById(Long id);


    /**
     * 根据菜单列表获取菜单信息
     *
     * @param ids
     * @return
     */
    List<SysMenu> getMenuListByIds(Collection<Long> ids);

    /**
     * 删除菜单
     *
     * @param id
     */
    void removeMenu(Long id);


    /**
     * 查询菜单树
     *
     * @param menuDTO
     * @return
     */
    List<SysMenu> menuList(MenuDTO menuDTO);

}
