package org.training.cloud.common.web.core.utils.date;

import java.util.Date;

/**
 * 时间工具类
 *
 * @author wangtongzhou 18635604249
 * @since 2023-04-09 14:17
 */
public class DateUtils {

    public static boolean isExpired(Date time) {
        return time.getTime() < System.currentTimeMillis();
    }
}
