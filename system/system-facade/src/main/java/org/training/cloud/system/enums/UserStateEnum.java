package org.training.cloud.system.enums;

/**
 * 用户状态
 *
 * @author wangtongzhou
 * @since 2020-08-20 21:47
 */
public enum UserStateEnum {

    NORMAL(0, "正常"),
    DISABLE(1, "禁用"),
    ;

    private Integer code;

    private String desc;

    UserStateEnum(Integer code, String desc) {
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
