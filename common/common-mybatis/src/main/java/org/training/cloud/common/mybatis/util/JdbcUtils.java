package org.training.cloud.common.mybatis.util;

import com.baomidou.mybatisplus.annotation.DbType;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC 工具类
 *
 * @author wangtongzhou
 * @since 2024-02-26 20:48
 */
public class JdbcUtils {

    /**
     * 判断连接是否正确
     *
     * @param url
     * @param username
     * @param password
     * @return
     */
    public static boolean isConnectionSuccess(String url, String username, String password) {
        try (Connection ignored = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }


}
