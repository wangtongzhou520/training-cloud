package org.training.cloud.common.core.utils.date;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;


/**
 * 时间工具类
 *
 * @author wangtongzhou
 */
public class DateUtils {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String TIME_PATTERN = "HH:mm:ss";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 根据格式解析日期字符串为 LocalDate 对象
     * @param dateString 日期字符串
     * @param pattern 日期格式
     * @return LocalDate 对象
     */
    public static LocalDate parseLocalDate(String dateString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDate.parse(dateString, formatter);
    }

    /**
     * 根据格式解析时间字符串为 LocalDateTime 对象
     * @param timeString 时间字符串
     * @param pattern 时间格式
     * @return LocalDateTime 对象
     */
    public static LocalDateTime parseLocalDateTime(String timeString, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(timeString, formatter);
    }

    /**
     * 将 LocalDate 对象格式化为字符串
     * @param localDate LocalDate 对象
     * @param pattern 日期格式
     * @return 格式化后的字符串
     */
    public static String formatLocalDate(LocalDate localDate, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(formatter);
    }

    /**
     * 将 LocalDateTime 对象格式化为字符串
     * @param localDateTime LocalDateTime 对象
     * @param pattern 时间格式
     * @return 格式化后的字符串
     */
    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return localDateTime.format(formatter);
    }

    /**
     * 获取当前日期字符串（yyyy-MM-dd）
     * @return 当前日期字符串
     */
    public static String getCurrentDateString() {
        return formatLocalDate(LocalDate.now(), DATE_PATTERN);
    }

    /**
     * 获取当前时间字符串（HH:mm:ss）
     * @return 当前时间字符串
     */
    public static String getCurrentTimeString() {
        return formatLocalDateTime(LocalDateTime.now(), TIME_PATTERN);
    }

    /**
     * 获取当前日期时间字符串（yyyy-MM-dd HH:mm:ss）
     * @return 当前日期时间字符串
     */
    public static String getCurrentDateTimeString() {
        return formatLocalDateTime(LocalDateTime.now(), DATE_TIME_PATTERN);
    }

    /**
     * 计算两个 LocalDateTime 之间的秒数差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 两个时间之间的秒数差
     */
    public static long getSecondsBetween(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).getSeconds();
    }

    /**
     * 计算两个 LocalDateTime 之间的分钟数差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 两个时间之间的分钟数差
     */
    public static long getMinutesBetween(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMinutes();
    }

    /**
     * 计算两个 LocalDateTime 之间的小时数差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 两个时间之间的小时数差
     */
    public static long getHoursBetween(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toHours();
    }

    /**
     * 计算两个 LocalDateTime 之间的天数差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 两个时间之间的天数差
     */
    public static long getDaysBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 计算两个 LocalDateTime 之间的月份差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 两个时间之间的月份差
     */
    public static long getMonthsBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.MONTHS.between(start, end);
    }

    /**
     * 计算两个 LocalDateTime 之间的年份差
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 两个时间之间的年份差
     */
    public static long getYearsBetween(LocalDateTime start, LocalDateTime end) {
        return ChronoUnit.YEARS.between(start, end);
    }


    /**
     * 是否过期
     *
     * @param time
     * @return
     */
    public static boolean isExpired(LocalDateTime time) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(time);
    }

    /**
     * 创建固定格式
     *
     * @param pattern
     * @return
     */
    public static DateTimeFormatter createFormatter(String pattern) {
        return DateTimeFormatter.ofPattern(pattern, Locale.getDefault()).withZone(ZoneId.systemDefault());
    }

}

