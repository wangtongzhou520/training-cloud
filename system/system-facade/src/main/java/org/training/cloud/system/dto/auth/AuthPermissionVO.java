package org.training.cloud.system.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.training.cloud.system.vo.user.UserVO;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * 用户的权限信息
 *
 * @author wangtongzhou
 * @since 2023-12-31 09:33
 */
@Schema(description = "用户的权限信息")
@Data
@Accessors(chain = true)
public class AuthPermissionVO implements Serializable {

    @Schema(description = "用户信息")
    private UserVO userVO;

    @Schema(description = "角色信息")
    private Set<String> roles;

    @Schema(description = "菜单信息")
    private List<MenuVO> menus;

    @Schema(description = "权限信息")
    private Set<String> permissions;



    @Schema(description = "菜单信息")
    @Data
    public static class MenuVO {

        @Schema(description = "菜单名称")
        private Long id;

        @Schema(description = "父菜单ID")
        private Long parentId;

        @Schema(description = "菜单名称")
        private String name;

        @Schema(description = "路由地址")
        private String path;

        @Schema(description = "组件路径")
        private String component;

        @Schema(description = "组件名")
        private String componentName;

        private String icon;

        /**
         * 子路由
         */
        private List<MenuVO> children;

    }

}
