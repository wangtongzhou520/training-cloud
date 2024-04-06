package org.training.cloud.tool.enums.generator;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 代码生成器场景
 *
 * @author wangtongzhou
 * @since 2024-03-03 16:17
 */
public enum GeneratorSceneEnum {

    ADMIN(1,"管理端"),

    APP(2,"APP端"),

            ;

    private Integer code;

    private String desc;

    GeneratorSceneEnum(Integer code, String desc) {
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
