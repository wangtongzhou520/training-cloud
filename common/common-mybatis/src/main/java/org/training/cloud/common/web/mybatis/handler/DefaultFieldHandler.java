package org.training.cloud.common.web.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.training.cloud.common.web.mybatis.dao.BaseDO;
import org.training.cloud.common.web.utils.WebUtil;

import java.util.Date;
import java.util.Objects;

/**
 * 通用参数自动赋值
 *
 * @author wangtongzhou 18635604249
 * @since 2023-02-05 10:07
 */
public class DefaultFieldHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();

            Date current = new Date();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getGmtCreate())) {
                baseDO.setGmtCreate(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseDO.getGmtModified())) {
                baseDO.setGmtModified(current);
            }

            Long userId = WebUtil.getLoginUserId();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getCreateOperator())) {
                baseDO.setCreateOperator(userId.toString());
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (Objects.nonNull(userId) && Objects.isNull(baseDO.getModifiedOperator())) {
                baseDO.setModifiedOperator(userId.toString());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("gmtModified", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("gmtModified", new Date(), metaObject);
        }

        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        Object modifier = getFieldValByName("modifiedOperator", metaObject);
        Long userId = WebUtil.getLoginUserId();
        if (Objects.nonNull(userId) && Objects.isNull(modifier)) {
            setFieldValByName("modifiedOperator", userId.toString(), metaObject);
        }
    }
}
