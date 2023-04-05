package org.training.cloud.system.entity.oauth2;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.web.mybatis.dao.BaseDO;

import java.util.Date;

/**
 * 刷新token
 *
 * @author wangtongzhou
 * @since 2020-09-18 12:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName(value = "oauth2_refresh_token")
public class Oauth2RefreshToken extends BaseDO {
    /**
     * 编号，刷新令牌
     */
    private String id;

    /**
     * 用户编号
     */
    private Long userId;

    /**
     * 过期时间
     */
    private Date expiresTime;

    /**
     * 创建者
     */
    private String createOperator;

    /**
     * 修改者
     */
    private String modifiedOperator;

    /**
     * 修改者ip
     */
    private String modifiedOperatorIp;

    /**
     * 是否删除
     */
    private String deleted;

}
