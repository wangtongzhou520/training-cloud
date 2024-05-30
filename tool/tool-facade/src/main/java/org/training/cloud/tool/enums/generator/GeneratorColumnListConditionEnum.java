package org.training.cloud.tool.enums.generator;

/**
 * 代码生成器的字段过滤条件枚举
 *
 * @author wangtongzhou
 * @since 2024-03-03 16:34
 */
public enum GeneratorColumnListConditionEnum {

    EQ("="),
    NE("!="),
    GT(">"),
    GTE(">="),
    LT("<"),
    LTE("<="),
    LIKE("LIKE"),
    BETWEEN("BETWEEN");

    /**
     * 条件
     */
    private String condition;


    GeneratorColumnListConditionEnum(String condition) {
        this.condition = condition;
    }


    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}
