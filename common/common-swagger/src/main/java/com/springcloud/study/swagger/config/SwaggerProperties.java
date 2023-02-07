package org.training.cloud.swagger.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Swagger基本属性
 *
 * @author wangtongzhou
 * @since 2020-05-24 16:58
 */
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    /**
     * 子系统
     */
    private String title;

    /**
     * 描述
     */
    private String description;

    /**
     * 版本号
     */
    private String version;

    /**
     * api
     */
    private String basePackage;

    public String getTitle() {
        return title;
    }

    public SwaggerProperties setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SwaggerProperties setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public SwaggerProperties setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public SwaggerProperties setBasePackage(String basePackage) {
        this.basePackage = basePackage;
        return this;
    }
}
