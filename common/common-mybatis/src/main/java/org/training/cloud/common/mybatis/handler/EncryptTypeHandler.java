package org.training.cloud.common.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.training.cloud.common.core.utils.aes.AESUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 字段加解密
 *
 * @author wangtongzhou
 * @since 2024-03-17 17:19
 */
public class EncryptTypeHandler extends BaseTypeHandler<String> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String parameter, JdbcType jdbcType) throws SQLException {
        try {
            preparedStatement.setString(i, AESUtils.encrypt(parameter));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        String value = resultSet.getString(columnName);
        try {
            return AESUtils.decrypt(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        String value = resultSet.getString(columnIndex);
        try {
            return AESUtils.decrypt(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        String value = callableStatement.getString(columnIndex);
        try {
            return AESUtils.decrypt(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
