package org.training.cloud.system.enums.permission;

/**
 * 菜单类型
 *
 * @author wangtongzhou
 * @since 2023-05-31 20:55
 */
public enum MenuTypeEnum {
    DIRECTORY(1, "目录"),
    MENU(2, "菜单"),
    BUTTON(3, "按钮"),
    ;

    private Integer code;

    private String desc;

    MenuTypeEnum(Integer code, String desc) {
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
