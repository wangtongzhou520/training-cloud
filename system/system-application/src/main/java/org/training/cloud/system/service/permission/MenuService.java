package org.training.cloud.system.service.permission;

import org.training.cloud.system.dto.permission.AddMenuDTO;
import org.training.cloud.system.dto.permission.ModifyMenuDTO;

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
     * 删除菜单
     *
     * @param id
     */
    void removeMenu(Long id);


    
}
