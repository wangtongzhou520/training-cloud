package org.training.cloud.tool.entity.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * 数据库配置
 *
 * @author wangtongzhou
 * @since 2024-02-25 21:21
 */
@Data
@TableName(value = "tool_data_source_config")
public class DataSourceConfig extends BaseDO {
    /**
     * 主键编号
     */
    private Long id;
    /**
     * 连接名
     */
    private String name;

    /**
     * 数据源连接
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

}
