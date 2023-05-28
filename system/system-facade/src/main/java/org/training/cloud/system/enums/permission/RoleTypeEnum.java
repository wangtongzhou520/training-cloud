package org.training.cloud.system.enums.permission;

/**
 * 角色类型
 *
 * @author wangtongzhou
 * @since 2023-05-26 07:24
 */
public enum RoleTypeEnum {
    ADMIN(1, "管理员"),
    OTHER(0, "其他"),
            ;

    private Integer code;

    private String desc;

    RoleTypeEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
