package org.training.cloud.system.enums.permission;

import java.util.HashMap;
import java.util.Map;

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

    private static Map<Integer, RoleTypeEnum> map = new HashMap<>();
    private static Map<String, RoleTypeEnum> descMap = new HashMap<>();


    static {
        for (RoleTypeEnum value : RoleTypeEnum.values()) {
            map.put(value.getCode(), value);
            descMap.put(value.getDesc(), value);
        }
    }

    public static RoleTypeEnum getByCode(Integer code) {
        return map.get(code);
    }

    public static RoleTypeEnum getByDesc(String desc) {
        return descMap.get(desc);
    }

}
