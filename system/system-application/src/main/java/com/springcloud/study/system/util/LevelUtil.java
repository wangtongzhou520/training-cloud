package com.springcloud.study.system.util;

import org.apache.commons.lang3.StringUtils;

/**
 * level工具类
 *
 * @author wangtongzhou
 * @since 2020-07-02 21:27
 */
public class LevelUtil {

    public final static String SEPARATOR = ".";

    public final static String ROOT = "0";

    /**
     * 计算当前部门的层级 例如下面例子
     * 0
     * 0.0
     * 0.1
     *
     * @param parentLevel 父级level
     * @param parentId    父级id
     * @return 层级
     */
    public static String calculateLevel(String parentLevel, int parentId) {
        //如果首层返会0
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        }
        return StringUtils.join(parentLevel, SEPARATOR, parentId);
    }
}
