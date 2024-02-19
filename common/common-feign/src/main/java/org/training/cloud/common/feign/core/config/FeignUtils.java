package org.training.cloud.common.feign.core.config;

import feign.RequestTemplate;
import feign.template.HeaderTemplate;
import feign.template.Literal;
import feign.template.Template;
import feign.template.TemplateChunk;
import org.training.cloud.common.core.utils.josn.JsonUtils;

import java.util.List;
import java.util.Map;

/**
 * 员工考勤信息
 *
 * @author wangtongzhou
 * @since 2023-07-27 20:36
 */
public class FeignUtils {
    @SuppressWarnings("unchecked")
    public static void createJsonHeader(RequestTemplate requestTemplate, String name, Object value) {
        if (value == null) {
            return;
        }
        // 添加 header
        String valueStr = JsonUtils.toJsonString(value);
        requestTemplate.header(name, valueStr);
//        // fix：由于 OpenFeign 针对 { 会进行分词，所以需要反射修改
//        // 具体分析，可见 https://zhuanlan.zhihu.com/p/360501330 文档
//        Map<String, HeaderTemplate> headers = (Map<String, HeaderTemplate>)
//                ReflectUtil.getFieldValue(requestTemplate, "headers");
//        HeaderTemplate template = headers.get(name);
//        List<Template> templateValues = (List<Template>)
//                ReflectUtil.getFieldValue(template, "values");
//        List<TemplateChunk> templateChunks = (List<TemplateChunk>)
//                ReflectUtil.getFieldValue(templateValues.get(0), "templateChunks");
//        templateChunks.set(0, Literal.create(valueStr));
    }
}
