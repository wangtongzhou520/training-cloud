package org.training.cloud.system.enums.user;

/**
 * 用户类型 此处可以设计不同的分类然后针对企业端和高校端设置不同的类型
 *
 * @author wangtongzhou
 * @since 2023-05-21 21:28
 */
public enum UserTypeEnum {

    USER(0, "C端"),
    ADMIN(1, "管理端"),
    ;

    private Integer code;

    private String desc;

    UserTypeEnum(Integer code, String desc) {
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
