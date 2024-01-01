package org.training.cloud.system.convert.auth;

import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.LoggerFactory;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.system.convert.user.UserConvert;
import org.training.cloud.system.dto.auth.AuthPermissionVO;
import org.training.cloud.system.entity.permission.SysMenu;
import org.training.cloud.system.entity.permission.SysRole;
import org.training.cloud.system.entity.user.SysUser;
import org.training.cloud.system.enums.permission.MenuTypeEnum;
import org.training.cloud.system.vo.auth.AuthLoginVO;
import org.training.cloud.system.vo.oauth2.Oauth2AccessTokenVO;
import org.training.cloud.system.vo.permission.MenuVO;
import org.training.cloud.system.vo.user.UserVO;

import java.util.*;

/**
 * 授权
 *
 * @author wangtongzhou
 * @since 2023-05-10 07:11
 */
@Mapper
public interface AuthConvert {

    AuthConvert INSTANCE = Mappers.getMapper(AuthConvert.class);

    /**
     * oauth2AccessTokenVO convert authLoginVO
     *
     * @param oauth2AccessTokenVO
     * @return
     */
    AuthLoginVO convert(Oauth2AccessTokenVO oauth2AccessTokenVO);


    default AuthPermissionVO convert(SysUser sysUser, List<SysRole> sysRoles,
                                     List<SysMenu> menus){
        AuthPermissionVO authPermissionVO=new AuthPermissionVO();
        authPermissionVO.setUserVO(UserConvert.INSTANCE.convert(sysUser));
        authPermissionVO.setRoles(CollectionExtUtils.convertSet(sysRoles,
                SysRole::getCode));
        authPermissionVO.setPermissions(CollectionExtUtils.convertSet(menus,
                SysMenu::getPermission));
        authPermissionVO.setMenus(buildMenuTree(menus));
        return authPermissionVO;
    }

    AuthPermissionVO.MenuVO convertTreeNode(SysMenu menu);


    default List<AuthPermissionVO.MenuVO> buildMenuTree(List<SysMenu> menus) {
        if (CollectionUtils.isEmpty(menus)) {
            return Collections.emptyList();
        }
        // 移除按钮
        menus.removeIf(menu -> menu.getType().equals(MenuTypeEnum.BUTTON.getCode()));
        // 排序，保证菜单的有序性
        menus.sort(Comparator.comparing(SysMenu::getSort));

        // 构建菜单树
        Map<Long, AuthPermissionVO.MenuVO> treeNodeMap = new LinkedHashMap<>();
        menus.forEach(menu -> treeNodeMap.put(menu.getId(), AuthConvert.INSTANCE.convertTreeNode(menu)));
        // 处理父子关系
        treeNodeMap.values().stream().filter(node -> !node.getParentId().equals(SysMenu.ROOT)).forEach(childNode -> {
            // 获得父节点
            AuthPermissionVO.MenuVO parentNode = treeNodeMap.get(childNode.getParentId());
            if (parentNode == null) {
                LoggerFactory.getLogger(getClass()).error("[buildRouterTree][resource({}) 找不到父资源({})]",
                        childNode.getId(), childNode.getParentId());
                return;
            }
            // 将自己添加到父节点中
            if (parentNode.getChildren() == null) {
                parentNode.setChildren(new ArrayList<>());
            }
            parentNode.getChildren().add(childNode);
        });
        // 获得到所有的根节点
        return CollectionExtUtils.filterList(treeNodeMap.values(),
                node -> SysMenu.ROOT.equals(node.getParentId()));
    }

}
