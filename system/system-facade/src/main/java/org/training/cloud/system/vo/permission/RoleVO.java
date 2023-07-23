package org.training.cloud.system.vo.permission;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 角色列表
 *
 * @author wangtongzhou 
 * @since 2023-01-18 17:45
 */
@Data
public class RoleVO implements Serializable {
    /**
     * 角色id
     */
    private Long id;

    /**
     * name
     */
    private String name;

    /**
     * 角色类型，1：管理员角色，2：其他
     */
    private Integer type;


    /**
     * 角色权限字符串
     */
    private String code;
}
