package org.training.cloud.tool.utils;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.google.common.collect.Maps;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.training.cloud.common.core.utils.collection.CollectionExtUtils;
import org.training.cloud.common.core.utils.date.DateUtils;
import org.training.cloud.common.core.vo.CommonResponse;
import org.training.cloud.common.core.vo.PageParam;
import org.training.cloud.common.core.vo.PageResponse;
import org.training.cloud.common.mybatis.dao.BaseDO;
import org.training.cloud.common.mybatis.extend.LambdaQueryWrapperExtend;
import org.training.cloud.common.mybatis.mapper.BaseMapperExtend;
import org.training.cloud.tool.config.generator.GeneratorProperties;
import org.training.cloud.tool.convert.generator.GeneratorTableConvert;
import org.training.cloud.tool.entity.generator.ToolGeneratorColumn;
import org.training.cloud.tool.entity.generator.ToolGeneratorTable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 生成表工具类
 *
 * @author wangtongzhou
 * @since 2024-03-03 15:31
 */
@Component
@Slf4j
public class GeneratorTableUtil {


    @Resource
    private GeneratorProperties generatorProperties;

    /**
     * 服务端模版
     */
    private static final Map<String, String> SERVER_MAP = Maps.newLinkedHashMap();

    /**
     * 前端模版
     */
    private static final Map<String, String> FRONT_MAP = Maps.newLinkedHashMap();


    /**
     * 全局映射
     */
    private final Map<String, Object> globalBindingMap = new HashMap<>();


    @Resource
    private Configuration configuration;

    public static ToolGeneratorTable buildTable(TableInfo tableInfo) {
        ToolGeneratorTable table = GeneratorTableConvert.INSTANCE.convert(tableInfo);
        initTableDefault(table);
        return table;
    }

    private static void initTableDefault(ToolGeneratorTable table) {
        // sys_user  模块名为sys、业务名为user、类名为SysUser
        String tableName = table.getTableName().toLowerCase();
        String[] arrays = tableName.split("_");
        table.setModuleName(arrays[0].toLowerCase());
        //业务名必须小写
        StringBuilder businessName = new StringBuilder();
        for (int i = 1; i < arrays.length; i++) {
            businessName.append(arrays[i]);
        }
        table.setBusinessName(businessName.toString().toLowerCase());
        //没有_就是表名
        if (StringUtils.isBlank(businessName)) {
            table.setBusinessName(tableName.toLowerCase());
        }
        //类名驼峰命名
        StringBuilder className = new StringBuilder();
        for (int i = 1; i < arrays.length; i++) {
            char firstChar = arrays[i].charAt(0);
            if (Character.isLowerCase(firstChar)) {
                className.append(Character.toUpperCase(firstChar)).append(arrays[i].substring(1));
            }
        }
        if (StringUtils.isNotBlank(className)) {
            table.setClassName(className.toString());
        } else {
            char firstChar = tableName.charAt(0);
            table.setClassName(Character.toUpperCase(firstChar) + tableName.substring(1));
        }
        //类描述
        table.setClassComment(table.getTableComment());
    }


    @PostConstruct
    void initMap() {
        //全局配置
        globalBindingMap.put("basePackage", generatorProperties.getBasePackage());


        //返回结果
        globalBindingMap.put("CommonResponse", CommonResponse.class.getName());
        globalBindingMap.put("PageResponseClassName", PageResponse.class.getName());
        //DTO公共字段
        globalBindingMap.put("PageParam", PageParam.class.getName());
        //DAL公共字段
        globalBindingMap.put("BaseDO", BaseDO.class.getName());
        //mybatis plus公共字段
        globalBindingMap.put("LambdaQueryWrapperExtend", LambdaQueryWrapperExtend.class.getName());
        globalBindingMap.put("BaseMapperExtend", BaseMapperExtend.class.getName());
        //工具类
        globalBindingMap.put("DateUtils", DateUtils.class.getName());
        globalBindingMap.put("CollectionExtUtils", CollectionExtUtils.class.getName());
        //服务端模版初始化
        //dal
        //entity
        SERVER_MAP.put(templatePath("entity"), javaEntityFilePath("application"));
        //dto
        SERVER_MAP.put(templatePath("pageParam"), javaClassNameFilePath("", "DTO", "dto", "facade"));
        SERVER_MAP.put(templatePath("add"), javaClassNameFilePath("Add", "DTO", "dto", "facade"));
        SERVER_MAP.put(templatePath("modify"), javaClassNameFilePath("Modify", "DTO", "dto", "facade"));
        //vo
        SERVER_MAP.put(templatePath("vo"), javaClassNameFilePath("", "VO", "vo", "facade"));
        //mapper
        SERVER_MAP.put(templatePath("mapper"), javaClassNameFilePath("", "Mapper", "mapper", "application"));
        //convert
        SERVER_MAP.put(templatePath("convert"), javaClassNameFilePath("", "Convert", "convert", "application"));
        //enum
        SERVER_MAP.put(templatePath("exceptionEnum"), javaClassNameFilePath("", "ExceptionEnumConstants", "constant", "application"));
        //service
        SERVER_MAP.put(templatePath("service"), javaClassNameFilePath("", "Srvice", "service", "application"));
        SERVER_MAP.put(templatePath("serviceImpl"), javaClassNameFilePath("", "ServiceImpl", "service", "application"));
        //controller
        SERVER_MAP.put(templatePath("controller"), javaClassNameFilePath("", "Controller", "controller", "application"));

        //前端代码初始化
        //vue
        FRONT_MAP.put(templatePath("indexVue"), vueFilePath("/views/${table.moduleName}/${smallClassName}/index.vue"));
        //from vue
        FRONT_MAP.put(templatePath("formVue"), vueFilePath("/views/${table.moduleName}/${smallClassName}/${table.className}Form.vue"));
        //api
        FRONT_MAP.put(templatePath("api"), vueFilePath("/api/${table.moduleName}/${smallClassName}.js"));
    }


    private Map<String, Object> initBindingMap(ToolGeneratorTable table, List<ToolGeneratorColumn> columns) {

        Map<String, Object> paramsMap = new HashMap<>(globalBindingMap);
        paramsMap.put("table", table);
        paramsMap.put("columns", columns);
        String[] arrays = table.getTableName().split("_");
        StringBuilder symbolCase = new StringBuilder();
        StringBuilder upperCase = new StringBuilder();
        StringBuilder smallClassName = new StringBuilder();
        for (int i = 1; i < arrays.length; i++) {
            symbolCase.append(arrays[i]);
            symbolCase.append("-");

            upperCase.append(arrays[i].toUpperCase());
            upperCase.append("-");

            smallClassName.append(arrays[i]);
        }
        //sys_oauth2_authorization_code  ->  oauth2-authorization-code
        paramsMap.put("symbolCaseClassName", symbolCase.substring(0, symbolCase.length() - 1));
        //Oauth2AuthorizationCode -> oauth2AuthorizationCode
        paramsMap.put("firstLowerClassName", Character.toLowerCase(table.getClassName().charAt(0)) + table.getClassName().substring(1));
        //system -> System
        paramsMap.put("upperModuleName", Character.toLowerCase(table.getModuleName().charAt(0)) + table.getModuleName().substring(1));
        //sys_oauth2_authorization_code  ->  OAUTH2_AUTHORIZATION_CODE
        paramsMap.put("upperCaseClassName", upperCase.substring(0, upperCase.length() - 1));
        //sys_oauth2_authorization_code  ->  oauth2authorizationcode
        paramsMap.put("smallClassName", smallClassName.toString());
        //类名小驼峰
        paramsMap.put("lowerCameClassName", Character.toLowerCase(table.getClassName().charAt(0)) + table.getClassName().substring(1));
        //权限前缀
        paramsMap.put("permissionPrefix", table.getModuleName() + ":" + smallClassName);


        return paramsMap;
    }


    private Map<String, String> getTemplates() {
        Map<String, String> templates = new LinkedHashMap<>();
        templates.putAll(SERVER_MAP);
        templates.putAll(FRONT_MAP);
        return templates;
    }


    public Map<String, String> execute(ToolGeneratorTable table, List<ToolGeneratorColumn> columns) {
        //初始化
        Map<String, Object> bindingMap = initBindingMap(table, columns);
        //获取对应模版
        Map<String, String> templates = getTemplates();
        Map<String, String> result = Maps.newLinkedHashMapWithExpectedSize(templates.size());
        templates.forEach((key, value) -> {
            generateCode(result, key, value, bindingMap);
        });
        return result;
    }

    private void generateCode(Map<String, String> result, String vmPath, String filePath, Map<String, Object> bindingMap) {
        filePath = formatFilePath(filePath, bindingMap);
        try {
            StringWriter writer = new StringWriter();
            Template template = configuration.getTemplate(vmPath);
            template.process(bindingMap, writer);
            result.put(filePath, writer.toString());
        } catch (Exception exception) {
            log.error(vmPath + "模版加载异常" + exception);
        }
    }

    private String formatFilePath(String filePath, Map<String, Object> bindingMap) {
        filePath = StringUtils.replace(filePath, "${basePackage}", bindingMap.get("basePackage").toString().replaceAll("\\.", "/"));
        // table 包含的字段
        ToolGeneratorTable table = (ToolGeneratorTable) bindingMap.get("table");
        filePath = StringUtils.replace(filePath, "${table.moduleName}", table.getModuleName());
        filePath = StringUtils.replace(filePath, "${table.businessName}", table.getBusinessName());
        filePath = StringUtils.replace(filePath, "${table.className}", table.getClassName());
        filePath = StringUtils.replace(filePath, "${smallClassName}", bindingMap.get("smallClassName").toString());
        return filePath;
    }


    private static String templatePath(String path) {
        return path + ".ftl";
    }


    private static String vueFilePath(String path) {
        return "training-admin-vue/src" + path;
    }


    private static String javaEntityFilePath(String module) {
        return "${table.moduleName}/" + "${table.moduleName}-" + module + "/" + "src/main/java/${basePackage}/${table.moduleName}" + "/entity/${smallClassName}/${table.className}.java";
    }


    private static String javaClassNameFilePath(String prefix, String suffix, String classify, String module) {
        return javaModuleFilePath(prefix + "${table.className}" + suffix, module, classify);
    }


    private static String javaModuleFilePath(String path, String module, String classify) {
        return "${table.moduleName}/" + "${table.moduleName}-" + module + "/" + "src/main/java/${basePackage}/${table.moduleName}/" + classify + "/${smallClassName}/" + path + ".java";
    }
}
