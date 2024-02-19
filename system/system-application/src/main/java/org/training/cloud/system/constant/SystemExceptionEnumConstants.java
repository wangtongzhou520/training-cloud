package org.training.cloud.system.constant;

import org.training.cloud.common.core.constant.ExceptionCode;

/**
 * 系统异常枚举
 *
 * @author wangtongzhou
 * @since 2020-09-18
 */
public interface SystemExceptionEnumConstants {


    /**
     * Oauth2 token
     */
    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_FOUND = new ExceptionCode(100001001, "访问令牌不存在");
    ExceptionCode OAUTH2_ACCESS_TOKEN_NOT_EXPIRED = new ExceptionCode(100001002, "访问令牌已过期");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_FOUND = new ExceptionCode(100001003, "刷新令牌不存在");
    ExceptionCode OAUTH2_REFRESH_TOKEN_NOT_EXPIRED = new ExceptionCode(100001004, "刷新令牌已过期");

    /**
     * Oauth2 client
     */
    ExceptionCode OAUTH2_CLIENT_EXIST = new ExceptionCode(100001005, "授权客户端编号已存在");
    ExceptionCode OAUTH2_CLIENT_NOT_EXIST = new ExceptionCode(100001006, "授权客户端不存在");
    ExceptionCode OAUTH2_CLIENT_DISABLE = new ExceptionCode(100001007,
            "授权客户端被禁用");
    ExceptionCode OAUTH2_CLIENT_SECRET_ERROR = new ExceptionCode(100001008,
            "无效client_secret");
    ExceptionCode OAUTH2_CLIENT_AUTHORIZED_GRANT_TYPE_NOT_EXISTS = new ExceptionCode(100001009,
            "授权码类型不支持");
    ExceptionCode OAUTH2_CLIENT_SCOPE_NOT_EXISTS = new ExceptionCode(100001009,
            "授权码范围不支持");
    ExceptionCode OAUTH2_CLIENT_REDIRECT_URI_NOT_MATCH =
            new ExceptionCode(100001010, "授权重定向地址不匹配");

    /**
     * Oauth2 Authorization Code
     */
    ExceptionCode OAUTH2_AUTHORIZATION_CODE_NOT_EXISTS =
            new ExceptionCode(100001011, "授权码不存在");
    ExceptionCode OAUTH2_AUTHORIZATION_CODE_NOT_EXPIRED =
            new ExceptionCode(100001012, "授权码过期");


    /**
     * Oauth2 授权
     */
    ExceptionCode OAUTH2_GRANT_CLIENT_ID_NOT_MATCH =
            new ExceptionCode(100001013, "授权客户端不匹配");
    ExceptionCode OAUTH2_GRANT_REDIRECT_URL_NOT_MATCH =
            new ExceptionCode(100001014, "授权重定向地址不匹配");
    ExceptionCode OAUTH2_GRANT_STATE_NOT_MATCH =
            new ExceptionCode(100001015, "授权状态不匹配");
    /**
     * auth
     */
    ExceptionCode AUTH_NOT_FOUND = new ExceptionCode(100002001,
            "登录失败，用户名或者密码不存在");
    ExceptionCode AUTH_ACCOUNT_FAIL = new ExceptionCode(100002002,
            "登录失败，用户名或者密码不正确");
    ExceptionCode AUTH_ACCOUNT_DISABLE = new ExceptionCode(100002003,
            "登录失败，用户名不可用");


    /**
     * user
     */
    ExceptionCode USER_PHONE_EXISTS = new ExceptionCode(100003001,
            "用户电话已经存在");
    ExceptionCode USER_MAIL_EXISTS = new ExceptionCode(100003002,
            "用户邮箱已经存在");
    ExceptionCode USER_NOT_EXISTS = new ExceptionCode(100003003,
            "用户不存在");

    /**
     * dept
     */
    ExceptionCode DEPT_NAME_EXISTS = new ExceptionCode(100004001, "部门名称已经存在");
    ExceptionCode DEPT_NOT_EXISTS = new ExceptionCode(100004002, "部门不存在");


    /**
     * 角色
     */
    ExceptionCode ROLE_ADMIN_CODE_ERROR = new ExceptionCode(100005001,
            "超级管理员不允许创建");

    ExceptionCode ROLE_NAME_EXISTS = new ExceptionCode(100005002,
            "角色信息已经存在");

    ExceptionCode ROLE_CODE_EXISTS = new ExceptionCode(100005003,
            "角色code已经存在");
    ExceptionCode ROLE_NOT_EXISTS = new ExceptionCode(100005004,
            "角色信息不存在");
    ExceptionCode ROLE_TYPE_NOT_EXISTS = new ExceptionCode(100005004,
            "角色类型不存在");


    /**
     * 菜单
     */
    ExceptionCode MENU_PARENT_ERROR = new ExceptionCode(100006001,
            "不允许设置自己为父节点");
    ExceptionCode MENU_PARENT_NOT_EXISTS = new ExceptionCode(100006002,
            "父菜单不存在");
    ExceptionCode MENU_PARENT_IS_MENU_DIR = new ExceptionCode(100006003,
            "父菜单类型必须是菜单或者目录");
    ExceptionCode MENU_NAME_EXISTS = new ExceptionCode(100006004,
            "菜单名称已存在");
    ExceptionCode MENU_NOT_EXISTS = new ExceptionCode(100006005,
            "菜单不存在");
    ExceptionCode MENU_EXISTS_CHILD = new ExceptionCode(100006006,
            "存在子菜单不允许删除");
}
