package org.training.cloud.system.entity.oauth2;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.training.cloud.common.mybatis.dao.BaseDO;

/**
 * 授权资源的管理
 *
 * @author wangtongzhou 
 * @since 2023-04-16 15:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_oauth2_scope_code", autoResultMap = true)
@Accessors(chain = true)
public class SysOauth2ScopeCode extends BaseDO {

    @TableId(type = IdType.AUTO)
    /**
     * 自增编号
     */
    private Long id;

    /**
     * 授权范围
     */
    private String scopeName;

    /**
     * 授权范围描述
     */
    private String scopeDescription;

}
