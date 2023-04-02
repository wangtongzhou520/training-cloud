package org.training.cloud.system.enums.oauth2;

/**
 * 授权客户端状态
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-02 15:29
 */
public enum Oauth2ClientStateEnum {

    NORMAL(0, "正常"),
    DELETE(1, "删除"),
            ;

    private Integer code;

    private String desc;

    Oauth2ClientStateEnum(Integer code, String desc) {
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
