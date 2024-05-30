package org.training.cloud.tool.enums.generator;

/**
 * 代码生成器的字段HTML展示
 *
 * @author wangtongzhou
 * @since 2024-03-03 16:57
 */
public enum GeneratorColumnHtmlTypeEnum {

    //文本框
    INPUT("input"),
    //文本域
    TEXTAREA("textarea"),
    //下拉框
    SELECT("select"),
    //单选框
    RADIO("radio"),
    //复选框
    CHECKBOX("checkbox"),
    //日期控件
    DATETIME("datetime"),
    //上传图片
    IMAGE_UPLOAD("imageUpload"),
    //上传文件
    FILE_UPLOAD("fileUpload"),
    //富文本控件
    EDITOR("editor"),
    ;


    private String type;


    GeneratorColumnHtmlTypeEnum(String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
