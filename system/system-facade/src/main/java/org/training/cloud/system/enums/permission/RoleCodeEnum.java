package org.training.cloud.system.enums.permission;

/**
 * 角色类型
 *
 * @author wangtongzhou
 * @since 2023-05-26 07:21
 */
public enum RoleCodeEnum {

    SUPER_ADMIN("super_admin", "超级管理员"),
            ;

    private String code;

    private String desc;

    RoleCodeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
