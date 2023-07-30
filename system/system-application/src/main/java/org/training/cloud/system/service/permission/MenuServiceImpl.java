package org.training.cloud.system.service.permission;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.training.cloud.common.core.exception.BusinessException;
import org.training.cloud.system.convert.permission.MenuConvert;
import org.training.cloud.system.dao.permission.SysMenuMapper;
import org.training.cloud.system.dto.permission.AddMenuDTO;
import org.training.cloud.system.dto.permission.ModifyMenuDTO;
import org.training.cloud.system.entity.permission.SysMenu;
import org.training.cloud.system.enums.permission.MenuTypeEnum;

import javax.annotation.Resource;
import java.util.Objects;

import static org.training.cloud.system.constant.SystemExceptionEnumConstants.*;

/**
 * 菜单
 *
 * @author wangtongzhou
 * @since 2023-05-30 21:58
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private SysMenuMapper sysMenuMapper;

    /**
     * root节点
     */
    private final Long ROOT_NODE_ID = 0L;


    @Override
    public void addMenu(AddMenuDTO addMenuDTO) {
        //检查父节点
        checkParentMenu(addMenuDTO.getParentId(), null);
        //检查相同节点下有没有相同菜单名字
        checkMenu(addMenuDTO.getParentId(), addMenuDTO.getName(), null);
        //插入
        SysMenu sysMenu = MenuConvert.INSTANCE.convert(addMenuDTO);
        sysMenuMapper.insert(sysMenu);

    }

    @Override
    public void modifyMenu(ModifyMenuDTO modifyMenuDTO) {
        //检查菜单是否存在
        checkExistById(modifyMenuDTO.getId());
        //检查父节点
        checkParentMenu(modifyMenuDTO.getParentId(), modifyMenuDTO.getId());
        //检查相同节点下有没有相同菜单名字
        checkMenu(modifyMenuDTO.getParentId(), modifyMenuDTO.getName(), modifyMenuDTO.getId());
        SysMenu sysMenu = MenuConvert.INSTANCE.convert(modifyMenuDTO);

        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeMenu(Long id) {
        //查询菜单下面是否存在子菜单
        checkExistChildMenu(id);
        //检查菜单是否存在
        checkExistById(id);
        //删除
        sysMenuMapper.deleteById(id);
        //删除角色信息

    }


    private void checkParentMenu(Long parentId, Long childId) {
        //根节点 为空或者为0
        if (parentId == null || ROOT_NODE_ID.equals(parentId)) {
            return;
        }
        //不允许自己为父节点
        if (parentId.equals(childId)) {
            throw new BusinessException(MENU_PARENT_ERROR);
        }
        //检查父节点是否存在
        SysMenu sysMenu = sysMenuMapper.selectById(parentId);
        if (Objects.isNull(sysMenu)) {
            throw new BusinessException(MENU_PARENT_NOT_EXISTS);
        }

        if (!MenuTypeEnum.DIRECTORY.getCode().equals(sysMenu.getType())
                && !MenuTypeEnum.MENU.getCode().equals(sysMenu.getType())) {
            throw new BusinessException(MENU_PARENT_IS_MENU_DIR);
        }
    }


    private void checkMenu(Long parentId, String name, Long id) {
        SysMenu sysMenu = sysMenuMapper.selectByParentIdAndMenuName(parentId, name);
        if (Objects.nonNull(sysMenu)) {
            if (!sysMenu.getId().equals(id)) {
                throw new BusinessException(MENU_NAME_EXISTS);
            }
        }
    }

    private void checkExistById(Long id) {
        SysMenu sysMenu = sysMenuMapper.selectById(id);
        if (Objects.isNull(sysMenu)) {
            throw new BusinessException(MENU_NOT_EXISTS);
        }
    }

    private void checkExistChildMenu(Long id) {
        Long count = sysMenuMapper.selectCountByParentId(id);
        if (count > 0) {
            throw new BusinessException(MENU_EXISTS_CHILD);
        }
    }
}
