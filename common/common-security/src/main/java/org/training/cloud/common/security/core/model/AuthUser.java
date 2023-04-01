package org.training.cloud.common.security.core.model;

import lombok.Data;

import java.util.List;

/**
 * 登录用户信息
 *
 * @author wangtongzhou 18635604249
 * @since 2023-03-30 21:24
 */
@Data
public class AuthUser {

    /**
     * 用户编号
     */
    private Long id;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 授权范围
     */
    private List<String> scopes;
}
