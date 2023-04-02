package org.training.cloud.common.mybatis.dao;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;


/**
 * 基础实体对象
 *
 * @author wangtongzhou
 * @since 2020-05-25 08:02
 */
public class BaseDO implements Serializable {

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;

    /**
     * 创建者
     */
    private String createOperator;

    /**
     * 修改者
     */
    private String modifiedOperator;




    public Date getGmtCreate() {
        return gmtCreate;
    }

    public BaseDO setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
        return this;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public BaseDO setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
        return this;
    }

    @Override
    public String toString() {
        return (new ToStringBuilder(this)).toString();
    }

}
