package org.training.cloud.tool.utils;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;
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
public class GeneratorTableUtil {


    @Resource
    private GeneratorProperties generatorProperties;

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
        //类名驼峰命名
        StringBuilder className = new StringBuilder();
        for (int i = 1; i < arrays.length; i++) {
            char firstChar = arrays[i].charAt(0);
            if (Character.isLowerCase(firstChar)) {
                className.append(Character.toUpperCase(firstChar)).append(arrays[i].substring(1));
            }
        }
        table.setClassName(className.toString().toLowerCase());
        //类描述
        table.setClassComment(table.getTableComment());
    }


    private static final Map<String, String> SERVER_MAP = Maps.newLinkedHashMap();

    /**
     * 全局映射
     */
    private final Map<String, Object> globalBindingMap = new HashMap<>();


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
        //服务端模版初始化
        //dal
        //entity
        SERVER_MAP.put(javaTemplatePath("dal/entity"),
                javaEntityFilePath("application"));


        //dto
        SERVER_MAP.put(javaTemplatePath("dto/pageParam"),
                javaModuleImplFilePath("", "DTO"));
        SERVER_MAP.put(javaTemplatePath("dto/add"), javaModuleImplFilePath("Add", "DTO"));
        SERVER_MAP.put(javaTemplatePath("dto/modify"), javaModuleImplFilePath("Modify", "DTO"));
        //vo
        SERVER_MAP.put(javaTemplatePath("dto/vo"), javaModuleImplFilePath("", "VO"));
        //controller


    }


    private Map<String, Object> initBindingMap(ToolGeneratorTable table, List<ToolGeneratorColumn> columns) {

        Map<String, Object> paramsMap = new HashMap<>(globalBindingMap);
        paramsMap.put("table", table);
        paramsMap.put("columns", columns);

        //单独处理树表
        return paramsMap;
    }


    private Map<String, String> getTemplates() {
        Map<String, String> templates = new LinkedHashMap<>();
        templates.putAll(SERVER_MAP);
//        templates.putAll(FRONT_TEMPLATES.row(frontType));
        return templates;
    }


    public void execute(ToolGeneratorTable table, List<ToolGeneratorColumn> columns) {
        //初始化
        Map<String, Object> bindingMap = initBindingMap(table, columns);

        //获取对应模版
        Map<String, String> templates = getTemplates();

        templates.forEach((key, value) -> {

        });

    }


    private static String javaTemplatePath(String path) {
        return "generator/java/" + path + ".vm";
    }


    private static String javaEntityFilePath(String module) {
        return "${table.moduleName}/" +
                "${table.moduleName}-" + module + "/" +
                "src/main/java/${basePackage}/${table.moduleName}" +
                "/entity/${table.moduleName}/${table.className}.java";
    }


    private static String javaModuleImplFilePath(String prefix, String suffix) {
        return javaModuleFilePath(prefix + "${table.className}" + suffix,
                "facade", suffix);
    }


    private static String javaModuleFilePath(String path, String module, String suffix) {
        return "${table.moduleName}/" +
                "${table.moduleName}-" + module + "/" +
                "src/main/java/${basePackage}/${table.moduleName}/" + suffix + "/" + path + ".java";
    }
}
