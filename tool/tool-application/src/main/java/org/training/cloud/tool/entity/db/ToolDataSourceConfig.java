package org.training.cloud.tool.entity.db;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;
import org.training.cloud.common.mybatis.handler.EncryptTypeHandler;

/**
 * 数据库配置
 *
 * @author wangtongzhou
 * @since 2024-02-25 21:21
 */
@TableName(value = "tool_data_source_config", autoResultMap = true)
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ToolDataSourceConfig extends BaseDO {
    /**
     * 主键编号
     */
    @TableId
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
    @TableField(typeHandler = EncryptTypeHandler.class)
    private String password;

}
