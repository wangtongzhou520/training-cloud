package org.training.cloud.system.enums.oauth2;

import java.util.HashMap;
import java.util.Map;

/**
 * oauth2授权类型
 *
 * @author wangtongzhou
 * @since 2023-04-02 20:37
 */
public enum OAuth2GrantTypeEnum {

    //密码模式
    PASSWORD("password"),
    //授权码模式
    AUTHORIZATION_CODE("authorization_code"),
    //简化模式
    IMPLICIT("implicit"),
    //客户端模式
    CLIENT_CREDENTIALS("client_credentials"),
    //刷新模式
    REFRESH_TOKEN("refresh_token"),
    ;

    OAuth2GrantTypeEnum(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    private String desc;


    private static Map<String, OAuth2GrantTypeEnum> descMap = new HashMap<>();


    static {
        for (OAuth2GrantTypeEnum value : OAuth2GrantTypeEnum.values()) {
            descMap.put(value.getDesc(), value);
        }
    }


    public static OAuth2GrantTypeEnum getByDesc(String desc) {
        return descMap.get(desc);
    }


}
