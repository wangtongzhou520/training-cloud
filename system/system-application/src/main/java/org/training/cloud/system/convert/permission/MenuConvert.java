package org.training.cloud.system.convert.permission;

import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.training.cloud.system.dto.permission.AddMenuDTO;
import org.training.cloud.system.dto.permission.ModifyMenuDTO;
import org.training.cloud.system.entity.permission.SysMenu;

/**
 * 菜单
 *
 * @author wangtongzhou
 * @since 2023-06-01 20:51
 */
@Mapper
public interface MenuConvert {

    MenuConvert INSTANCE = Mappers.getMapper(MenuConvert.class);

    /**
     * addMenuDTO convert sysMenu
     *
     * @param addMenuDTO
     * @return
     */
    @Mappings({})
    SysMenu convert(AddMenuDTO addMenuDTO);

    /**
     * modifyMenuDTO convert sysMenu
     *
     * @param modifyMenuDTO
     * @return
     */
    @Mappings({})
    SysMenu convert(ModifyMenuDTO modifyMenuDTO);

}
