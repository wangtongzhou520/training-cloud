package org.training.cloud.getway.utils;

import java.util.List;

/**
 * 登录用户信息
 *
 * @author wangtongzhou 
 * @since 2023-03-30 21:24
 */
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


    public Long getId() {
        return id;
    }

    public AuthUser setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getUserType() {
        return userType;
    }

    public AuthUser setUserType(Integer userType) {
        this.userType = userType;
        return this;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public AuthUser setScopes(List<String> scopes) {
        this.scopes = scopes;
        return this;
    }

}
