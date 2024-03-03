package org.training.cloud.tool.utils;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import org.training.cloud.tool.convert.generator.GeneratorTableConvert;
import org.training.cloud.tool.entity.generator.ToolGeneratorTable;

/**
 * 生成表工具类
 *
 * @author wangtongzhou
 * @since 2024-03-03 15:31
 */
public class GeneratorTableUtil {

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
}
