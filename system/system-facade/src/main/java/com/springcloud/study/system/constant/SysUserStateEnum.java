package com.springcloud.study.system.constant;

/**
 * 用户状态枚举
 *
 * @author wangtongzhou
 * @since 2020-08-20 21:47
 */
public enum SysUserStateEnum {

    NORMAL(1, "正常"),
    FROZEN(2, "冻结"),
    DELETE(3, "删除"),
    ;

    private Integer code;

    private String desc;

    SysUserStateEnum(Integer code, String desc) {
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
